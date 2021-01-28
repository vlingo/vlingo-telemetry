// Copyright © 2012-2020 VLINGO LABS. All rights reserved.
//
// This Source Code Form is subject to the terms of the
// Mozilla Public License, v. 2.0. If a copy of the MPL
// was not distributed with this file, You can obtain
// one at https://mozilla.org/MPL/2.0/.

package io.vlingo.telemetry.plugin.mailbox;

import java.util.Properties;

import brave.Tracer;
import brave.Tracing;
import io.vlingo.actors.Configuration;
import io.vlingo.actors.Registrar;
import io.vlingo.actors.plugin.Plugin;
import io.vlingo.actors.plugin.PluginConfiguration;
import io.vlingo.actors.plugin.PluginProperties;
import io.vlingo.actors.plugin.mailbox.DefaultMailboxProviderKeeper;
import io.vlingo.telemetry.Telemetry;
import io.vlingo.telemetry.tracing.TracingBaggage;
import zipkin2.reporter.brave.AsyncZipkinSpanHandler;
import zipkin2.reporter.brave.ZipkinSpanHandler;
import zipkin2.reporter.okhttp3.OkHttpSender;

public class MailboxTelemetryPlugin implements Plugin {
  public static class MailboxTelemetryPluginConfiguration implements PluginConfiguration {
    private static final String NO_NAME = "_No_Name_";

    @Override
    public void build(final Configuration configuration) {
      buildWith(configuration, new PluginProperties(NO_NAME, new Properties()));
    }

    @Override
    public void buildWith(final Configuration configuration, final PluginProperties properties) {
    }

    @Override
    public String name() {
      return MailboxTelemetryPlugin.class.getSimpleName();
    }
  }

  private final MailboxTelemetryPluginConfiguration configuration;

  public MailboxTelemetryPlugin() {
    this(new MailboxTelemetryPluginConfiguration());
  }

  @Override
  public void close() {

  }

  @Override
  public PluginConfiguration configuration() {
    return configuration;
  }

  @Override
  public String name() {
    return configuration.name();
  }

  @Override
  public int pass() {
    return 0;
  }

  @Override
  public void start(final Registrar registrar) {
    Telemetry<?> telemetry = Telemetry.from(registrar.world());
    OkHttpSender sender = OkHttpSender.create("http://127.0.0.1:9411/api/v2/spans");
//   (this dependency is io.zipkin.reporter2:zipkin-reporter-brave)
    ZipkinSpanHandler zipkinSpanHandler = AsyncZipkinSpanHandler.create(sender);

// Create a tracing component with the service name you want to see in Zipkin.
    Tracing tracing = Tracing.newBuilder()
            .traceId128Bit(true)
            .addSpanHandler(zipkinSpanHandler)
            .build();

// Tracing exposes objects you might need, most importantly the tracer
    Tracer tracer = tracing.tracer();
    TracingBaggage baggage = TracingBaggage.withTracer(tracer);

    registrar.registerMailboxProviderKeeper(new TelemetryMailboxProviderKeeper(new DefaultMailboxProviderKeeper(), new DefaultMailboxTelemetry(telemetry), baggage));
  }

  @Override
  public Plugin with(final PluginConfiguration overrideConfiguration) {
    if (overrideConfiguration == null) {
      return this;
    }
    return new MailboxTelemetryPlugin((MailboxTelemetryPluginConfiguration) overrideConfiguration);
  }

  @Override
  public void __internal_Only_Init(final String name, final Configuration configuration, final Properties properties) {
    // no-op
  }

  private MailboxTelemetryPlugin(final MailboxTelemetryPluginConfiguration configuration) {
    this.configuration = configuration;
  }
}

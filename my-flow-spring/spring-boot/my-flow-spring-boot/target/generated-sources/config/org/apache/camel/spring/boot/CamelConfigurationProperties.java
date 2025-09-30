/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.spring.boot;

import org.apache.camel.LoggingLevel;
import org.apache.camel.ManagementMBeansLevel;
import org.apache.camel.ManagementStatisticsLevel;
import org.apache.camel.StartupSummaryLevel;
import org.apache.camel.main.DefaultConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.DeprecatedConfigurationProperty;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.Map;

@ConfigurationProperties(prefix = "camel")
public class CamelConfigurationProperties  {

    @NestedConfigurationProperty
    private final Main main = new Main();

    public Main getMain() {
        return main;
    }

    public class Main extends InnerCamelConfigurationProperties {

        /**
         * Whether to use the main run controller to ensure the Spring-Boot application
         * keeps running until being stopped or the JVM terminated.
         * You typically only need this if you run Spring-Boot standalone.
         * If you run Spring-Boot with spring-boot-starter-web then the web container keeps the JVM running.
         */
        private boolean runController;

        /**
         * Whether to use the main run controller to ensure the Spring-Boot application
         * keeps running until being stopped or the JVM terminated.
         * You typically only need this if you run Spring-Boot standalone.
         * If you run Spring-Boot with spring-boot-starter-web then the web container keeps the JVM running.
         */
        @Deprecated
        private boolean mainRunController;

        /**
         * Whether to include non-singleton beans (prototypes) when scanning for RouteBuilder instances.
         * By default only singleton beans is included in the context scan.
         */
        private boolean includeNonSingletons;

        /**
         * Whether to log a WARN if Camel on Spring Boot was immediately shutdown after starting which
         * very likely is because there is no JVM thread to keep the application running.
         */
        private boolean warnOnEarlyShutdown = true;

        public boolean isRunController() {
            return runController;
        }

        public void setRunController(boolean runController) {
            this.runController = runController;
        }

        @DeprecatedConfigurationProperty(replacement = "camel.main.run-controller", since = "4.14.0")
        @Deprecated
        public boolean isMainRunController() {
            return mainRunController;
        }

        @Deprecated
        public void setMainRunController(boolean mainRunController) {
            this.mainRunController = mainRunController;
        }

        public boolean isIncludeNonSingletons() {
            return includeNonSingletons;
        }

        public void setIncludeNonSingletons(boolean includeNonSingletons) {
            this.includeNonSingletons = includeNonSingletons;
        }

        public boolean isWarnOnEarlyShutdown() {
            return warnOnEarlyShutdown;
        }

        public void setWarnOnEarlyShutdown(boolean warnOnEarlyShutdown) {
            this.warnOnEarlyShutdown = warnOnEarlyShutdown;
        }

    }

    @SuppressWarnings("all")
    static class InnerCamelConfigurationProperties extends DefaultConfigurationProperties {

        /**
         * Sets the name of the CamelContext.
         */
        private java.lang.String name;
        /**
         * Sets the description (intended for humans) of the Camel application.
         */
        private java.lang.String description;
        /**
         * Controls the level of information logged during startup (and shutdown) of CamelContext.
         */
        private org.apache.camel.StartupSummaryLevel startupSummaryLevel;
        /**
         * To specify for how long time in seconds to keep running the JVM before automatic terminating the JVM. You can use
         * this to run Camel for a short while.
         */
        private int durationMaxSeconds;
        /**
         * To specify for how long time in seconds Camel can be idle before automatic terminating the JVM. You can use this
         * to run Camel for a short while.
         */
        private int durationMaxIdleSeconds;
        /**
         * To specify how many messages to process by Camel before automatic terminating the JVM. You can use this to run
         * Camel for a short while.
         */
        private int durationMaxMessages;
        /**
         * Controls whether the Camel application should shutdown the JVM, or stop all routes, when duration max is
         * triggered.
         */
        private java.lang.String durationMaxAction = "shutdown";
        /**
         * Timeout in seconds to graceful shutdown all the Camel routes.
         */
        private int shutdownTimeout = 45;
        /**
         * Whether Camel should try to suppress logging during shutdown and timeout was triggered, meaning forced shutdown
         * is happening. And during forced shutdown we want to avoid logging errors/warnings et all in the logs as a
         * side-effect of the forced timeout. Notice the suppress is a best effort as there may still be some logs coming
         * from 3rd party libraries and whatnot, which Camel cannot control. This option is default false.
         */
        private boolean shutdownSuppressLoggingOnTimeout;
        /**
         * Sets whether to force shutdown of all consumers when a timeout occurred and thus not all consumers was shutdown
         * within that period.
         *
         * You should have good reasons to set this option to false as it means that the routes keep running and is halted
         * abruptly when CamelContext has been shutdown.
         */
        private boolean shutdownNowOnTimeout = true;
        /**
         * Sets whether routes should be shutdown in reverse or the same order as they were started.
         */
        private boolean shutdownRoutesInReverseOrder = true;
        /**
         * Sets whether to log information about the inflight Exchanges which are still running during a shutdown which
         * didn't complete without the given timeout.
         *
         * This requires to enable the option inflightRepositoryBrowseEnabled.
         */
        private boolean shutdownLogInflightExchangesOnTimeout = true;
        /**
         * Sets whether the inflight repository should allow browsing each inflight exchange.
         *
         * This is by default disabled as there is a very slight performance overhead when enabled.
         */
        private boolean inflightRepositoryBrowseEnabled;
        /**
         * Directory to load additional properties files that contains configurations that takes precedence (except for
         * camel.main.xxx configurations).
         *
         * This can be used to refer to files that may have secret configuration that has been mounted on the file system
         * for containers.
         *
         * You can specify a pattern to load from file-system (not classpath) and a name pattern such as
         * /var/app/secret/*.properties, multiple directories can be separated by comma.
         */
        private java.lang.String fileConfigurations;
        /**
         * Enable JMX in your Camel application.
         */
        private boolean jmxEnabled = true;
        /**
         * UUID generator to use.
         *
         * default (32 bytes), short (16 bytes), classic (32 bytes or longer), simple (long incrementing counter), off
         * (turned off for exchanges - only intended for performance profiling)
         */
        private java.lang.String uuidGenerator = "default";
        /**
         * Producer template endpoints cache size.
         */
        private int producerTemplateCacheSize = 1000;
        /**
         * Consumer template endpoints cache size.
         */
        private int consumerTemplateCacheSize = 1000;
        /**
         * Whether to load custom type converters by scanning classpath. This is used for backwards compatibility with Camel
         * 2.x. Its recommended to migrate to use fast type converter loading by setting <tt>@Converter(loader = true)</tt>
         * on your custom type converter classes.
         */
        private boolean loadTypeConverters;
        /**
         * Whether to load custom health checks by scanning classpath.
         */
        private boolean loadHealthChecks;
        /**
         * Whether to enable developer console (requires camel-console on classpath).
         *
         * The developer console is only for assisting during development. This is NOT for production usage.
         */
        private boolean devConsoleEnabled;
        /**
         * Whether to support JBang style //DEPS to specify additional dependencies when running Camel JBang
         */
        private boolean modeline;
        /**
         * Is used to limit the maximum length of the logging Camel message bodies. If the message body is longer than the
         * limit, the log message is clipped. Use -1 to have unlimited length. Use for example 1000 to log at most 1000
         * characters.
         */
        private int logDebugMaxChars;
        /**
         * Sets whether stream caching is enabled or not.
         *
         * While stream types (like StreamSource, InputStream and Reader) are commonly used in messaging for performance
         * reasons, they also have an important drawback: they can only be read once. In order to be able to work with
         * message content multiple times, the stream needs to be cached.
         *
         * Streams are cached in memory only (by default).
         *
         * If streamCachingSpoolEnabled=true, then, for large stream messages (over 128 KB by default) will be cached in a
         * temporary file instead, and Camel will handle deleting the temporary file once the cached stream is no longer
         * necessary.
         *
         * Default is true.
         */
        private boolean streamCachingEnabled = true;
        /**
         * To filter stream caching of a given set of allowed/denied classes. By default, all classes that are
         * {@link java.io.InputStream} is allowed. Multiple class names can be separated by comma.
         */
        private java.lang.String streamCachingAllowClasses;
        /**
         * To filter stream caching of a given set of allowed/denied classes. By default, all classes that are
         * {@link java.io.InputStream} is allowed. Multiple class names can be separated by comma.
         */
        private java.lang.String streamCachingDenyClasses;
        /**
         * To enable stream caching spooling to disk. This means, for large stream messages (over 128 KB by default) will be
         * cached in a temporary file instead, and Camel will handle deleting the temporary file once the cached stream is
         * no longer necessary.
         *
         * Default is false.
         */
        private boolean streamCachingSpoolEnabled;
        /**
         * Sets the stream caching spool (temporary) directory to use for overflow and spooling to disk.
         *
         * If no spool directory has been explicit configured, then a temporary directory is created in the java.io.tmpdir
         * directory.
         */
        private java.lang.String streamCachingSpoolDirectory;
        /**
         * Sets a stream caching cipher name to use when spooling to disk to write with encryption. By default the data is
         * not encrypted.
         */
        private java.lang.String streamCachingSpoolCipher;
        /**
         * Stream caching threshold in bytes when overflow to disk is activated. The default threshold is 128kb. Use -1 to
         * disable overflow to disk.
         */
        private long streamCachingSpoolThreshold;
        /**
         * Sets a percentage (1-99) of used heap memory threshold to activate stream caching spooling to disk.
         */
        private int streamCachingSpoolUsedHeapMemoryThreshold;
        /**
         * Sets what the upper bounds should be when streamCachingSpoolUsedHeapMemoryThreshold is in use.
         */
        private java.lang.String streamCachingSpoolUsedHeapMemoryLimit;
        /**
         * Sets whether if just any of the org.apache.camel.spi.StreamCachingStrategy.SpoolRule rules returns true then
         * shouldSpoolCache(long) returns true, to allow spooling to disk. If this option is false, then all the
         * org.apache.camel.spi.StreamCachingStrategy.SpoolRule must return true.
         *
         * The default value is false which means that all the rules must return true.
         */
        private boolean streamCachingAnySpoolRules;
        /**
         * Sets the stream caching buffer size to use when allocating in-memory buffers used for in-memory stream caches.
         *
         * The default size is 4096.
         */
        private int streamCachingBufferSize;
        /**
         * Whether to remove stream caching temporary directory when stopping. This option is default true.
         */
        private boolean streamCachingRemoveSpoolDirectoryWhenStopping = true;
        /**
         * Sets whether stream caching statistics is enabled.
         */
        private boolean streamCachingStatisticsEnabled;
        /**
         * Sets whether type converter statistics is enabled.
         *
         * By default the type converter utilization statistics is disabled. Notice: If enabled then there is a slight
         * performance impact under very heavy load.
         */
        private boolean typeConverterStatisticsEnabled;
        /**
         * Sets whether tracing is enabled or not.
         *
         * Default is false.
         */
        private boolean tracing;
        /**
         * Whether to set tracing on standby. If on standby then the tracer is installed and made available. Then the tracer
         * can be enabled later at runtime via JMX or via {@link Tracer#setEnabled(boolean)}.
         */
        private boolean tracingStandby;
        /**
         * Whether tracing should trace inner details from route templates (or kamelets). Turning this on increases the
         * verbosity of tracing by including events from internal routes in the templates or kamelets.
         *
         * Default is false.
         */
        private boolean tracingTemplates;
        /**
         * Tracing pattern to match which node EIPs to trace. For example to match all To EIP nodes, use to*. The pattern
         * matches by node and route id's Multiple patterns can be separated by comma.
         */
        private java.lang.String tracingPattern;
        /**
         * To use a custom tracing logging format.
         *
         * The default format (arrow, routeId, label) is: %-4.4s [%-12.12s] [%-33.33s]
         */
        private java.lang.String tracingLoggingFormat;
        /**
         * Sets whether message history is enabled or not.
         *
         * Default is false.
         */
        private boolean messageHistory;
        /**
         * Whether to capture precise source location:line-number for all EIPs in Camel routes.
         *
         * Enabling this will impact parsing Java based routes (also Groovy etc.) on startup as this uses JDK
         * StackTraceElement to calculate the location from the Camel route, which comes with a performance cost. This only
         * impact startup, not the performance of the routes at runtime.
         */
        private boolean sourceLocationEnabled;
        /**
         * Sets whether log mask is enabled or not.
         *
         * Default is false.
         */
        private boolean logMask;
        /**
         * Sets whether to log exhausted message body with message history.
         *
         * Default is false.
         */
        private boolean logExhaustedMessageBody;
        /**
         * The global name to use for Log EIP
         *
         * The name is default the routeId or the source:line if source location is enabled. You can also specify the name
         * using tokens:
         *
         * <br/>
         * ${class} - the logger class name (org.apache.camel.processor.LogProcessor) <br/>
         * ${contextId} - the camel context id <br/>
         * ${routeId} - the route id <br/>
         * ${groupId} - the route group id <br/>
         * ${nodeId} - the node id <br/>
         * ${nodePrefixId} - the node prefix id <br/>
         * ${source} - the source:line (source location must be enabled) <br/>
         * ${source.name} - the source filename (source location must be enabled) <br/>
         * ${source.line} - the source line number (source location must be enabled)
         *
         * For example to use the route and node id you can specify the name as: ${routeId}/${nodeId}
         */
        private java.lang.String logName;
        /**
         * To configure the language to use for Log EIP. By default, the simple language is used. However, Camel also
         * supports other languages such as groovy.
         */
        private java.lang.String logLanguage;
        /**
         * Camel comes with a default set of sensitive keywords which are automatically masked. This option allows to add
         * additional custom keywords to be masked as well. Multiple keywords can be separated by comma.
         */
        private java.lang.String additionalSensitiveKeywords;
        /**
         * Sets whether the object should automatically start when Camel starts. Important: Currently only routes can be
         * disabled, as CamelContext's are always started. Note: When setting auto startup false on CamelContext then that
         * takes precedence and no routes are started. You would need to start CamelContext explicit using the
         * org.apache.camel.CamelContext.start() method, to start the context, and then you would need to start the routes
         * manually using CamelContext.getRouteController().startRoute(String).
         *
         * Default is true to always start up.
         */
        private boolean autoStartup = true;
        /**
         * Used for exclusive filtering of routes to not automatically start with Camel starts.
         *
         * The pattern support matching by route id or endpoint urls.
         *
         * Multiple patterns can be specified separated by comma, as example, to exclude all the routes starting from kafka
         * or jms use: kafka,jms.
         */
        private java.lang.String autoStartupExcludePattern;
        /**
         * Sets whether to allow access to the original message from Camel's error handler, or from
         * org.apache.camel.spi.UnitOfWork.getOriginalInMessage(). Turning this off can optimize performance, as defensive
         * copy of the original message is not needed.
         *
         * Default is false.
         */
        private boolean allowUseOriginalMessage;
        /**
         * Whether to use case sensitive or insensitive headers.
         *
         * Important: When using case sensitive (this is set to false). Then the map is case sensitive which means headers
         * such as content-type and Content-Type are two different keys which can be a problem for some protocols such as
         * HTTP based, which rely on case insensitive headers. However case sensitive implementations can yield faster
         * performance. Therefore use case sensitive implementation with care.
         *
         * Default is true.
         */
        private boolean caseInsensitiveHeaders = true;
        /**
         * Whether autowiring is enabled. This is used for automatic autowiring options (the option must be marked as
         * autowired) by looking up in the registry to find if there is a single instance of matching type, which then gets
         * configured on the component. This can be used for automatic configuring JDBC data sources, JMS connection
         * factories, AWS Clients, etc.
         *
         * Default is true.
         */
        private boolean autowiredEnabled = true;
        /**
         * Sets whether endpoint runtime statistics is enabled (gathers runtime usage of each incoming and outgoing
         * endpoints).
         *
         * The default value is false.
         */
        private boolean endpointRuntimeStatisticsEnabled;
        /**
         * Sets whether Camel load (inflight messages, not cpu) statistics is enabled (something like the unix load
         * average). The statistics requires to have camel-management on the classpath as JMX is required.
         *
         * The default value is false.
         */
        private boolean loadStatisticsEnabled;
        /**
         * Whether the producer should be started lazy (on the first message). By starting lazy you can use this to allow
         * CamelContext and routes to startup in situations where a producer may otherwise fail during starting and cause
         * the route to fail being started. By deferring this startup to be lazy then the startup failure can be handled
         * during routing messages via Camel's routing error handlers. Beware that when the first message is processed then
         * creating and starting the producer may take a little time and prolong the total processing time of the
         * processing.
         *
         * The default value is false.
         */
        private boolean endpointLazyStartProducer;
        /**
         * Allows for bridging the consumer to the Camel routing Error Handler, which mean any exceptions occurred while the
         * consumer is trying to pickup incoming messages, or the likes, will now be processed as a message and handled by
         * the routing Error Handler.
         * <p/>
         * By default the consumer will use the org.apache.camel.spi.ExceptionHandler to deal with exceptions, that will be
         * logged at WARN/ERROR level and ignored.
         *
         * The default value is false.
         */
        private boolean endpointBridgeErrorHandler;
        /**
         * Whether to enable using data type on Camel messages.
         *
         * Data type are automatic turned on if one ore more routes has been explicit configured with input and output
         * types. Otherwise data type is default off.
         */
        private boolean useDataType;
        /**
         * Set whether breadcrumb is enabled. The default value is false.
         */
        private boolean useBreadcrumb;
        /**
         * Can be used to turn off bean post processing.
         *
         * Be careful to turn this off, as this means that beans that use Camel annotations such as
         * {@link org.apache.camel.EndpointInject}, {@link org.apache.camel.ProducerTemplate},
         * {@link org.apache.camel.Produce}, {@link org.apache.camel.Consume} etc will not be injected and in use.
         *
         * Turning this off should only be done if you are sure you do not use any of these Camel features.
         *
         * Not all runtimes allow turning this off.
         *
         * The default value is true (enabled).
         */
        private boolean beanPostProcessorEnabled = true;
        /**
         * Sets the mbeans registration level.
         *
         * The default value is Default.
         */
        private org.apache.camel.ManagementMBeansLevel jmxManagementMBeansLevel = ManagementMBeansLevel.Default;
        /**
         * Sets the JMX statistics level, the level can be set to Extended to gather additional information
         *
         * The default value is Default.
         */
        private org.apache.camel.ManagementStatisticsLevel jmxManagementStatisticsLevel = ManagementStatisticsLevel.Default;
        /**
         * The naming pattern for creating the CamelContext JMX management name.
         *
         * The default pattern is #name#
         */
        private java.lang.String jmxManagementNamePattern = "#name#";
        /**
         * Whether routes created by Kamelets should be registered for JMX management. Enabling this allows to have
         * fine-grained monitoring and management of every route created via Kamelets.
         *
         * This is default disabled as a Kamelet is intended as a component (black-box) and its implementation details as
         * Camel route makes the overall management and monitoring of Camel applications more verbose.
         *
         * During development of Kamelets then enabling this will make it possible for developers to do fine-grained
         * performance inspection and identify potential bottlenecks in the Kamelet routes.
         *
         * However, for production usage then keeping this disabled is recommended.
         */
        private boolean jmxManagementRegisterRoutesCreateByKamelet;
        /**
         * Whether routes created by route templates (not Kamelets) should be registered for JMX management. Enabling this
         * allows to have fine-grained monitoring and management of every route created via route templates.
         *
         * This is default enabled (unlike Kamelets) as routes created via templates is regarded as standard routes, and
         * should be available for management and monitoring.
         */
        private boolean jmxManagementRegisterRoutesCreateByTemplate = true;
        /**
         * Whether to include timestamps for all emitted Camel Events. Enabling this allows to know fine-grained at what
         * time each event was emitted, which can be used for reporting to report exactly the time of the events. This is by
         * default false to avoid the overhead of including this information.
         */
        private boolean camelEventsTimestampEnabled;
        /**
         * To turn on MDC logging
         */
        private boolean useMdcLogging;
        /**
         * Sets the pattern used for determine which custom MDC keys to propagate during message routing when the routing
         * engine continues routing asynchronously for the given message. Setting this pattern to * will propagate all
         * custom keys. Or setting the pattern to foo*,bar* will propagate any keys starting with either foo or bar. Notice
         * that a set of standard Camel MDC keys are always propagated which starts with camel. as key name.
         *
         * The match rules are applied in this order (case insensitive):
         *
         * 1. exact match, returns true 2. wildcard match (pattern ends with a * and the name starts with the pattern),
         * returns true 3. regular expression match, returns true 4. otherwise returns false
         */
        private java.lang.String mdcLoggingKeysPattern;
        /**
         * Sets the thread name pattern used for creating the full thread name.
         *
         * The default pattern is: Camel (#camelId#) thread ##counter# - #name#
         *
         * Where #camelId# is the name of the CamelContext. and #counter# is a unique incrementing counter. and #name# is
         * the regular thread name.
         *
         * You can also use #longName# which is the long thread name which can includes endpoint parameters etc.
         */
        private java.lang.String threadNamePattern;
        /**
         * Used for filtering routes matching the given pattern, which follows the following rules:
         *
         * - Match by route id - Match by route input endpoint uri
         *
         * The matching is using exact match, by wildcard and regular expression as documented by
         * {@link PatternHelper#matchPattern(String, String)}.
         *
         * For example to only include routes which starts with foo in their route id's, use: include=foo&#42; And to
         * exclude routes which starts from JMS endpoints, use: exclude=jms:&#42;
         *
         * Multiple patterns can be separated by comma, for example to exclude both foo and bar routes, use:
         * exclude=foo&#42;,bar&#42;
         *
         * Exclude takes precedence over include.
         */
        private java.lang.String routeFilterIncludePattern;
        /**
         * Used for filtering routes routes matching the given pattern, which follows the following rules:
         *
         * - Match by route id - Match by route input endpoint uri
         *
         * The matching is using exact match, by wildcard and regular expression as documented by
         * {@link PatternHelper#matchPattern(String, String)}.
         *
         * For example to only include routes which starts with foo in their route id's, use: include=foo&#42; And to
         * exclude routes which starts from JMS endpoints, use: exclude=jms:&#42;
         *
         * Multiple patterns can be separated by comma, for example to exclude both foo and bar routes, use:
         * exclude=foo&#42;,bar&#42;
         *
         * Exclude takes precedence over include.
         */
        private java.lang.String routeFilterExcludePattern;
        /**
         * Sets whether bean introspection uses extended statistics. The default is false.
         */
        private boolean beanIntrospectionExtendedStatistics;
        /**
         * Sets the logging level used by bean introspection, logging activity of its usage. The default is TRACE.
         */
        private org.apache.camel.LoggingLevel beanIntrospectionLoggingLevel;
        /**
         * Whether the routes collector is enabled or not.
         *
         * When enabled Camel will auto-discover routes (RouteBuilder instances from the registry and also load additional
         * routes from the file system).
         *
         * The routes collector is default enabled.
         */
        private boolean routesCollectorEnabled = true;
        /**
         * Whether the routes collector should ignore any errors during loading and compiling routes.
         *
         * This is only intended for development or tooling.
         */
        private boolean routesCollectorIgnoreLoadingError;
        /**
         * Work directory for compiler. Can be used to write compiled classes or other resources.
         */
        private java.lang.String compileWorkDir;
        /**
         * Used for inclusive filtering RouteBuilder classes which are collected from the registry or via classpath
         * scanning. The exclusive filtering takes precedence over inclusive filtering. The pattern is using Ant-path style
         * pattern. Multiple patterns can be specified separated by comma.
         *
         * Multiple patterns can be specified separated by comma. For example to include all classes starting with Foo use:
         * &#42;&#42;/Foo* To include all routes form a specific package use: com/mycompany/foo/&#42; To include all routes
         * form a specific package and its sub-packages use double wildcards: com/mycompany/foo/&#42;&#42; And to include
         * all routes from two specific packages use: com/mycompany/foo/&#42;,com/mycompany/stuff/&#42;
         */
        private java.lang.String javaRoutesIncludePattern;
        /**
         * Used for exclusive filtering RouteBuilder classes which are collected from the registry or via classpath
         * scanning. The exclusive filtering takes precedence over inclusive filtering. The pattern is using Ant-path style
         * pattern. Multiple patterns can be specified separated by comma.
         *
         * For example to exclude all classes starting with Bar use: &#42;&#42;/Bar&#42; To exclude all routes form a
         * specific package use: com/mycompany/bar/&#42; To exclude all routes form a specific package and its sub-packages
         * use double wildcards: com/mycompany/bar/&#42;&#42; And to exclude all routes from two specific packages use:
         * com/mycompany/bar/&#42;,com/mycompany/stuff/&#42;
         */
        private java.lang.String javaRoutesExcludePattern;
        /**
         * Used for inclusive filtering of routes from directories. The exclusive filtering takes precedence over inclusive
         * filtering. The pattern is using Ant-path style pattern.
         *
         * Multiple patterns can be specified separated by comma, as example, to include all the routes from a directory
         * whose name contains foo use: &#42;&#42;/*foo*.
         */
        private java.lang.String routesIncludePattern = "classpath:camel/*,classpath:camel-template/*,classpath:camel-rest/*";
        /**
         * Used for exclusive filtering of routes from directories. The exclusive filtering takes precedence over inclusive
         * filtering. The pattern is using Ant-path style pattern.
         *
         * Multiple patterns can be specified separated by comma, as example, to exclude all the routes from a directory
         * whose name contains foo use: &#42;&#42;/*foo*.
         */
        private java.lang.String routesExcludePattern;
        /**
         * Used for enabling automatic routes reloading. If enabled then Camel will watch for file changes in the given
         * reload directory, and trigger reloading routes if files are changed.
         */
        private boolean routesReloadEnabled;
        /**
         * Used for enabling context reloading. If enabled then Camel allow external systems such as security vaults (AWS
         * secrets manager, etc.) to trigger refreshing Camel by updating property placeholders and reload all existing
         * routes to take changes into effect.
         */
        private boolean contextReloadEnabled;
        /**
         * Directory to scan for route changes. Camel cannot scan the classpath, so this must be configured to a file
         * directory. Development with Maven as build tool, you can configure the directory to be src/main/resources to scan
         * for Camel routes in XML or YAML files.
         */
        private java.lang.String routesReloadDirectory = "src/main/resources/camel";
        /**
         * Whether the directory to scan should include sub directories.
         *
         * Depending on the number of sub directories, then this can cause the JVM to startup slower as Camel uses the JDK
         * file-watch service to scan for file changes.
         */
        private boolean routesReloadDirectoryRecursive;
        /**
         * Used for inclusive filtering of routes from directories.
         *
         * Typical used for specifying to accept routes in XML or YAML files, such as <tt>*.yaml,*.xml</tt>. Multiple
         * patterns can be specified separated by comma.
         */
        private java.lang.String routesReloadPattern;
        /**
         * When reloading routes should all existing routes be stopped and removed.
         *
         * By default, Camel will stop and remove all existing routes before reloading routes. This ensures that only the
         * reloaded routes will be active. If disabled then only routes with the same route id is updated, and any existing
         * routes are continued to run.
         */
        private boolean routesReloadRemoveAllRoutes = true;
        /**
         * Whether to restart max duration when routes are reloaded. For example if max duration is 60 seconds, and a route
         * is reloaded after 25 seconds, then this will restart the count and wait 60 seconds again.
         */
        private boolean routesReloadRestartDuration;
        /**
         * Whether to allow updating routes at runtime via JMX using the ManagedRouteMBean.
         *
         * This is disabled by default, but can be enabled for development and troubleshooting purposes, such as updating
         * routes in an existing running Camel via JMX and other tools.
         */
        private boolean jmxUpdateRouteEnabled;
        /**
         * Directories to scan for groovy source to be pre-compiled. For example: scripts/*.groovy will scan inside the
         * classpath folder scripts for all groovy source files.
         *
         * By default, sources are scanned from the classpath, but you can prefix with file: to use file system.
         *
         * The directories are using Ant-path style pattern, and multiple directories can be specified separated by comma.
         *
         * This requires having camel-groovy JAR on the classpath.
         */
        private java.lang.String groovyScriptPattern = "classpath:camel-groovy/*,classpath:camel-groovy-compiled/*";
        /**
         * Whether to preload existing compiled Groovy sources from the compileWorkDir option on startup. This can be
         * enabled to avoid compiling sources that already has been compiled during a build phase.
         */
        private boolean groovyPreloadCompiled;
        /**
         * Controls whether to pool (reuse) exchanges or create new exchanges (prototype). Using pooled will reduce JVM
         * garbage collection overhead by avoiding to re-create Exchange instances per message each consumer receives. The
         * default is prototype mode.
         */
        private java.lang.String exchangeFactory = "default";
        /**
         * The capacity the pool (for each consumer) uses for storing exchanges. The default capacity is 100.
         */
        private int exchangeFactoryCapacity = 100;
        /**
         * Configures whether statistics is enabled on exchange factory.
         */
        private boolean exchangeFactoryStatisticsEnabled;
        /**
         * If dumping is enabled then Camel will during startup dump all loaded routes (incl rests and route templates)
         * represented as XML/YAML DSL into the log. This is intended for trouble shooting or to assist during development.
         *
         * Sensitive information that may be configured in the route endpoints could potentially be included in the dump
         * output and is therefore not recommended being used for production usage.
         *
         * This requires to have camel-xml-io/camel-yaml-io on the classpath to be able to dump the routes as XML/YAML.
         */
        private java.lang.String dumpRoutes;
        /**
         * Controls what to include in output for route dumping.
         *
         * Possible values: all, routes, rests, routeConfigurations, routeTemplates, beans, dataFormats. Multiple values can
         * be separated by comma. Default is routes.
         */
        private java.lang.String dumpRoutesInclude = "routes";
        /**
         * Whether to log route dumps to Logger
         */
        private boolean dumpRoutesLog = true;
        /**
         * Whether to resolve property placeholders in the dumped output. Default is true.
         */
        private boolean dumpRoutesResolvePlaceholders = true;
        /**
         * When dumping routes to YAML format, then this option controls whether endpoint URIs should be expanded into a
         * key/value parameters.
         */
        private boolean dumpRoutesUriAsParameters;
        /**
         * Whether to include auto generated IDs in the dumped output. Default is false.
         */
        private boolean dumpRoutesGeneratedIds;
        /**
         * Whether to save route dumps to an output file.
         *
         * If the output is a filename, then all content is saved to this file. If the output is a directory name, then one
         * or more files are saved to the directory, where the names are based on the original source file names, or auto
         * generated names.
         */
        private java.lang.String dumpRoutesOutput;
        /**
         * Sets global options that can be referenced in the camel context
         * <p/>
         * <b>Important:</b> This has nothing to do with property placeholders, and is just a plain set of key/value pairs
         * which are used to configure global options on CamelContext, such as a maximum debug logging length etc.
         */
        private java.util.Map globalOptions;
        /**
         * To use startup recorder for capturing execution time during starting Camel. The recorder can be one of: false (or
         * off), logging, backlog, java-flight-recorder (or jfr).
         */
        private java.lang.String startupRecorder;
        /**
         * To filter our sub steps at a maximum depth.
         *
         * Use -1 for no maximum. Use 0 for no sub steps. Use 1 for max 1 sub step, and so forth.
         *
         * The default is -1.
         */
        private int startupRecorderMaxDepth = -1;
        /**
         * To enable Java Flight Recorder to start a recording and automatic dump the recording to disk after startup is
         * complete.
         *
         * This requires that camel-jfr is on the classpath, and to enable this option.
         */
        private boolean startupRecorderRecording;
        /**
         * To use a specific Java Flight Recorder profile configuration, such as default or profile.
         *
         * The default is default.
         */
        private java.lang.String startupRecorderProfile = "default";
        /**
         * How long time to run the startup recorder.
         *
         * Use 0 (default) to keep the recorder running until the JVM is exited. Use -1 to stop the recorder right after
         * Camel has been started (to only focus on potential Camel startup performance bottlenecks) Use a positive value to
         * keep recording for N seconds.
         *
         * When the recorder is stopped then the recording is auto saved to disk (note: save to disk can be disabled by
         * setting startupRecorderDir to false)
         */
        private long startupRecorderDuration;
        /**
         * Directory to store the recording. By default the current directory will be used. Use false to turn off saving
         * recording to disk.
         */
        private java.lang.String startupRecorderDir;
        /**
         * Sets the locations (comma separated values) where to find properties configuration as defined for cloud native
         * environments such as Kubernetes. You should only scan text based mounted configuration.
         */
        private java.lang.String cloudPropertiesLocation;
    }
}

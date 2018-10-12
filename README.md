# CustomUndertowFilter

Custom filter/handler to intecept requests to wildfly server.



Now build CustomUndertowFilter and run the cli file to create the WildFly module
<WILDFLY_HOME>/bin/jboss-cli.sh --connect --file=add-custom-module.cli

To add the filter to server add the following configuration in the undertow subsystem.


- Decare the filter
```
<filters>
	...
	
	<filter name="myCustomHandler"
	module="lk.kana.elytron.custom-filter"
	class-name="lk.kana.elytron.custom_filter.CustomUndertowHandler"/>

	 <filter name="custom-timer-filter" 
	 		module="lk.kana.elytron.custom-filter:1" 
	 		class-name="lk.kana.elytron.custom_filter.ResponseTimeHandler">
	 		
         <param name="param1" value="value1"/>
	 </filter>

</filters>

```

- Add the folloing to host section under server to enable it to at the server level.

```
<host ...>

	<filter-ref name="myCustomHandler"/>
	
	<filter-ref name="custom-timer-filter"/>
...

</host>
```


- Apply a filter only to an application level

When doing this no need to declare it at the server configuration.

Add this module as a dependency to the application.

Under the web application META-INF declare it as a Servlet Extention (io.undertow.servlet.ServletExtension)

```

lk.kana.elytron.custom_filter.CustomUndertowHandler
 
```


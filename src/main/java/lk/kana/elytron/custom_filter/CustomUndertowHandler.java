package lk.kana.elytron.custom_filter;

import javax.servlet.ServletContext;

import org.jboss.logging.Logger;


import io.undertow.Handlers;
import io.undertow.server.HandlerWrapper;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.servlet.ServletExtension;
import io.undertow.servlet.api.DeploymentInfo;

public class CustomUndertowHandler implements HttpHandler, ServletExtension{

	private Logger log = Logger.getLogger(this.getClass());
	
	private HttpHandler next;

	
	// this is a must, other wise servlet extension causes a InstantiationException
	public CustomUndertowHandler() {
		super();
	}

	public CustomUndertowHandler(HttpHandler next) {
		super();
        this.next = next;
	}

	public void handleRequest(HttpServerExchange exchange) throws Exception {

		
		log.infof("Received request from %s to %s", exchange.getDestinationAddress().getHostName(), 
											        exchange.getDestinationAddress().getAddress());


		/**
		 * Capture the returning response for further processing
		 */
		PostExchangeListener mylistener = new PostExchangeListener();
        exchange.addExchangeCompleteListener(mylistener);

        next.handleRequest(exchange);
		
	}

	// Servlet extension calls here
	public void handleDeployment(DeploymentInfo deploymentInfo, ServletContext arg1) {
		deploymentInfo.addOuterHandlerChainWrapper(new HandlerWrapper() {

			//@Override
			public HttpHandler wrap(HttpHandler handler) {
				log.info("addOuterHandlerChainWrapper wrap");
				return Handlers.path()
		                .addPrefixPath("/", new CustomUndertowHandler(handler));
			}
		});
	}
	
	
}

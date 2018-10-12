package lk.kana.elytron.custom_filter;

import org.jboss.logging.Logger;

import io.undertow.server.ExchangeCompletionListener;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;

public class ResponseTimeHandler implements HttpHandler {

	private Logger log = Logger.getLogger(this.getClass());
	
	private HttpHandler next;
    private String param1;
	
    // this type of constructor must be implemented otherwise we gets a runtime exception
    // when tried to use this filter
    public ResponseTimeHandler(HttpHandler next) {
        this.next = next;
	}
    
	public void handleRequest(HttpServerExchange exchange) throws Exception {

		log.info("--- Received request to ResponseTimeHandler");

		StopWatch stopWatch = new StopWatch();
        stopWatch.setT0(System.currentTimeMillis());
        exchange.addExchangeCompleteListener(stopWatch);
        next.handleRequest(exchange);
		
	}

	public void setParam1(String s) {
        this.param1 = s;
    }
	
	private class StopWatch implements ExchangeCompletionListener {

        private long t0;

        /**
         * This code gets executed on one of XNIO Worker's worker thread (as opposite to the IO threads),
         *  after the exchange is completed.
         */
        //@Override
        public void exchangeEvent(HttpServerExchange exchange, NextListener nextListener) {

            try {

                // stop the watch and calculate the difference
                long t1 = System.currentTimeMillis();
                log.info("request took " + (t1 - t0) + " ms");
            }
            finally {

                if (nextListener != null) {
                    nextListener.proceed();
                }
            }
        }
        
        public void setT0(long t0) {
            this.t0 = t0;
        }
	}
}

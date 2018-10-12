package lk.kana.elytron.custom_filter;

import org.jboss.logging.Logger;

import io.undertow.server.ExchangeCompletionListener;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.ExchangeCompletionListener.NextListener;

class PostExchangeListener implements ExchangeCompletionListener {
	 
	
	private Logger log = Logger.getLogger(this.getClass());
    //@Override
    public void exchangeEvent(HttpServerExchange exchange, NextListener nextListener) {

        try {

           log.info("Reply from " + exchange.getSourceAddress().getHostName());
        } finally {

            if (nextListener != null) {
                nextListener.proceed();
            }
        }
    }

}

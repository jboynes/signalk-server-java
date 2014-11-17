/*
 * Copyright 2012,2013 Robert Huitema robert@42.co.nz
 * 
 * This file is part of FreeBoard. (http://www.42.co.nz/freeboard)
 * 
 * FreeBoard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * FreeBoard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with FreeBoard. If not, see <http://www.gnu.org/licenses/>.
 */
package nz.co.fortytwo.signalk.server;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

/**
 * Exports the signalkModel as a json object
 * 
 * @author robert
 * 
 */
public class SignalkModelExportProcessor extends FreeboardProcessor implements Processor{

	private static Logger logger = Logger.getLogger(SignalkModelExportProcessor.class);
	
	public void process(Exchange exchange) throws Exception {
		
		try {
			//exchange.getIn().setBody(signalkModel.safeDuplicate());
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}

	

}

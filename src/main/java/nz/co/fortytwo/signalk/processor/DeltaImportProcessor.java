/*
 *
 * Copyright (C) 2012-2014 R T Huitema. All Rights Reserved.
 * Web: www.42.co.nz
 * Email: robert@42.co.nz
 * Author: R T Huitema
 *
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING THE
 * WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package nz.co.fortytwo.signalk.processor;

import mjson.Json;
import nz.co.fortytwo.signalk.handler.DeltaToMapConverter;
import nz.co.fortytwo.signalk.model.SignalKModel;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.log4j.Logger;

/**
 * Updates the signalkModel with the current json
 * 
 * @author robert
 * 
 */
public class DeltaImportProcessor extends SignalkProcessor implements Processor{

	private static Logger logger = Logger.getLogger(DeltaImportProcessor.class);
	
	private DeltaToMapConverter deltaToMap = new DeltaToMapConverter();
	
	public void process(Exchange exchange) throws Exception {
		
		try {
			if(exchange.getIn().getBody()==null ||!(exchange.getIn().getBody() instanceof Json)) return;
			
			SignalKModel model = deltaToMap.handle(exchange.getIn().getBody(Json.class));
			
			if(model!=null){
				if(logger.isDebugEnabled())logger.debug("Converted to:"+model);
				exchange.getIn().setBody(model);
			}else{
				if(logger.isDebugEnabled())logger.debug("Ignored, not update:"+exchange.getIn().getBody(Json.class));
			}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
	}



	
}

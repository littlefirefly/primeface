/*
 * Copyright 2009-2011 Prime Technology.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.primefaces.examples.view;

import java.io.File;
import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.imageio.stream.FileImageOutputStream;
import javax.servlet.ServletContext;

import my.CaptureEvent;

import org.primefaces.context.RequestContext;
//import org.primefaces.event.CaptureEvent;

public class PhotoShare {
        
    private String getRandomImageName() {
		int i = (int) (Math.random() * 10000000);
		
		return String.valueOf(i);
	}
    
    public void share(CaptureEvent captureEvent) {
        String photo = getRandomImageName();
        byte[] data = captureEvent.getData();
        
		ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
		String newFileName = servletContext.getRealPath("") + File.separator + "photocam" + File.separator + photo + ".png";
		
		FileImageOutputStream imageOutput;
		try {
			imageOutput = new FileImageOutputStream(new File(newFileName));
			imageOutput.write(data, 0, data.length);
			imageOutput.close();
            
            RequestContext.getCurrentInstance().push("photoshare", photo + ".png");
		}
        catch(Exception e) {
			throw new FacesException("Error in writing captured image.");
		}
    }
}

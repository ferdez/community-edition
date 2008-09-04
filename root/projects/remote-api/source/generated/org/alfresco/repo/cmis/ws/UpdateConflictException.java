
package org.alfresco.repo.cmis.ws;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.0.6
 * Tue Jul 29 18:22:39 EEST 2008
 * Generated source version: 2.0.6
 * 
 */

@WebFault(name = "updateConflictException", targetNamespace = "http://www.cmis.org/ns/1.0")

public class UpdateConflictException extends Exception {
    public static final long serialVersionUID = 20080729182239L;
    
    private org.alfresco.repo.cmis.ws.BasicFault updateConflictException;

    public UpdateConflictException() {
        super();
    }
    
    public UpdateConflictException(String message) {
        super(message);
    }
    
    public UpdateConflictException(String message, Throwable cause) {
        super(message, cause);
    }

    public UpdateConflictException(String message, org.alfresco.repo.cmis.ws.BasicFault updateConflictException) {
        super(message);
        this.updateConflictException = updateConflictException;
    }

    public UpdateConflictException(String message, org.alfresco.repo.cmis.ws.BasicFault updateConflictException, Throwable cause) {
        super(message, cause);
        this.updateConflictException = updateConflictException;
    }

    public org.alfresco.repo.cmis.ws.BasicFault getFaultInfo() {
        return this.updateConflictException;
    }
}

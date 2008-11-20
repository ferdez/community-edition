/*
 * Copyright (C) 2005-2007 Alfresco Software Limited.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.

 * As a special exception to the terms and conditions of version 2.0 of 
 * the GPL, you may redistribute this Program in connection with Free/Libre 
 * and Open Source Software ("FLOSS") applications as described in Alfresco's 
 * FLOSS exception.  You should have recieved a copy of the text describing 
 * the FLOSS exception, and it is also available here: 
 * http://www.alfresco.com/legal/licensing"
 */
package org.alfresco.repo.web.scripts.dictionary;

import org.alfresco.web.scripts.Cache;
import org.alfresco.web.scripts.DeclarativeWebScript;
import org.alfresco.web.scripts.Status;
import org.alfresco.web.scripts.WebScriptException;
import org.alfresco.web.scripts.WebScriptRequest;
import org.alfresco.service.namespace.QName;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.dictionary.ClassDefinition;
import org.alfresco.service.cmr.dictionary.PropertyDefinition;
import org.alfresco.service.cmr.dictionary.AssociationDefinition;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Webscript to get the Sub-Classdefinitions using classfilter , namespaceprefix and name
 * @author Saravanan Sellathurai
 */

public class GetSubClassesDef extends DeclarativeWebScript
{
	private DictionaryService dictionaryservice;
	private DictionaryHelper dictionaryhelper;
	
	private static final String MODEL_PROP_KEY_CLASS_DEFS = "classdefs";
	private static final String MODEL_PROP_KEY_PROPERTY_DETAILS = "propertydefs";
	private static final String MODEL_PROP_KEY_ASSOCIATION_DETAILS = "assocdefs";
	
    private static final String REQ_URL_TEMPL_IMMEDIATE_SUB_TYPE_CHILDREN = "r";
    //private static final String REQ_URL_TEMPL_VAR_CLASS_FILTER = "cf";
    private static final String REQ_URL_TEMPL_VAR_NAMESPACE_PREFIX = "nsp";
    private static final String REQ_URL_TEMPL_VAR_NAME = "n";
    private static final String DICTIONARY_CLASS_NAME = "classname";
    
	/**
     * Set the dictionaryService property.
     * 
     * @param dictionaryService The dictionary service instance to set
     */
    public void setDictionaryService(DictionaryService dictionaryService)
    {
        this.dictionaryservice = dictionaryService; 
    }
    
    /**
     * Set the dictionaryhelper class
     * 
     * @param dictionaryService The dictionary service instance to set
     */
    public void setDictionaryHelper(DictionaryHelper dictionaryhelper)
    {
        this.dictionaryhelper = dictionaryhelper; 
    }
    
    /**
     * @Override  method from DeclarativeWebScript 
     */
    protected Map<String, Object> executeImpl(WebScriptRequest req, Status status, Cache cache)
    {
    	String name = req.getParameter(REQ_URL_TEMPL_VAR_NAME);
    	String namespaceprefix = req.getParameter(REQ_URL_TEMPL_VAR_NAMESPACE_PREFIX);
        String classname = req.getServiceMatch().getTemplateVars().get(DICTIONARY_CLASS_NAME);
        String recursive_value = this.dictionaryhelper.getValidInput(req.getParameter(REQ_URL_TEMPL_IMMEDIATE_SUB_TYPE_CHILDREN));
        
        boolean recursive = true;
        
        Map<QName, ClassDefinition> classdef = new HashMap<QName, ClassDefinition>();
        Map<QName, Collection<PropertyDefinition>> propdef = new HashMap<QName, Collection<PropertyDefinition>>();
        Map<QName, Collection<AssociationDefinition>> assocdef = new HashMap<QName, Collection<AssociationDefinition>>();
        Map<String, Object> model = new HashMap<String, Object>();
        
        QName classQName = null;
        String namespaceUri = null;
        Collection <QName> qname = null;
        boolean isAspect = false;
        boolean ignoreCheck = false;
        
        // validate recursive parameter => can be either true or false or null
        if(recursive_value == null)
        {
        	recursive = true;
        }
        else if(recursive_value.equalsIgnoreCase("true"))
        {
        	recursive = true;
        }
        else if (recursive_value.equalsIgnoreCase("false"))
        {
        	recursive = false;
        }
        else
        {
        	throw new WebScriptException(Status.STATUS_NOT_FOUND, "Check the value for the parameter recursive=> " + recursive_value +"  can only be either true or false");
        }
        	
        //validate the classname
        if(this.dictionaryhelper.isValidClassname(classname) == true)
        {
        	classQName = QName.createQName(this.dictionaryhelper.getFullNamespaceURI(classname));
        	if(this.dictionaryhelper.isValidTypeorAspect(classname) == true) 
        	{
        		isAspect = true;
        	}
        }
        else
        {
        	throw new WebScriptException(Status.STATUS_NOT_FOUND, "Check the classname - " + classname + " parameter in the URL");
        }
        
        // collect the subaspects or subtypes of the class
        if(isAspect == true) 
		{
    		qname = this.dictionaryservice.getSubAspects(classQName, recursive);
    	}
    	else
		{
			qname = this.dictionaryservice.getSubTypes(classQName, recursive);
		}
        
        //validate the namespaceprefix parameter
        if(namespaceprefix != null)
        {
        	if(this.dictionaryhelper.isValidPrefix(namespaceprefix) == false)
        	{
        		throw new WebScriptException(Status.STATUS_NOT_FOUND, "Check the namespaceprefix - " + namespaceprefix + " - parameter in the URL");
        	}
        }
        
        //validate the name parameter
        if(name != null)
        {
        	if(this.dictionaryhelper.isValidModelName(name) == false)
        	{
        		throw new WebScriptException(Status.STATUS_NOT_FOUND, "Check the name parameter - " + name + " in the URL");
        	}
        }
        
        //validate the name parameter
        if (namespaceprefix == null && name != null)
        {
        	namespaceUri = this.dictionaryhelper.getNamespaceURIfromPrefix(this.dictionaryhelper.getPrefixFromModelName(name));
        }
        
        if (namespaceprefix != null && name == null)
        {
        	namespaceUri = this.dictionaryhelper.getNamespaceURIfromPrefix(namespaceprefix);
        }
        
        if(namespaceprefix == null && name == null)
        {
        	namespaceUri = null;
        	ignoreCheck = true;
        }
        
        if (namespaceprefix != null && name != null)
        {
        	if(this.dictionaryhelper.isValidClassname(namespaceprefix + "_" + name) == false)
        	{
        		throw new WebScriptException(Status.STATUS_NOT_FOUND, "Check the namespaceprefix - " + namespaceprefix + " and name - "+ name  + " - parameter in the URL");
        	}
        	namespaceUri = this.dictionaryhelper.getNamespaceURIfromPrefix(namespaceprefix);
        }
        
        for(QName qnameObj: qname)
	    {	
			if(ignoreCheck || qnameObj.getNamespaceURI().equals(namespaceUri))
			{
	    		classdef.put(qnameObj, this.dictionaryservice.getClass(qnameObj));
	    		propdef.put(qnameObj, this.dictionaryservice.getClass(qnameObj).getProperties().values());
	    		assocdef.put(qnameObj, this.dictionaryservice.getClass(qnameObj).getAssociations().values());
			}
	    }
    	
    	model.put(MODEL_PROP_KEY_CLASS_DEFS, classdef.values());
	    model.put(MODEL_PROP_KEY_PROPERTY_DETAILS, propdef.values());
	    model.put(MODEL_PROP_KEY_ASSOCIATION_DETAILS, assocdef.values());
	    return model;        	
		
	 }
   
 }
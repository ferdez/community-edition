/*
 * Copyright (C) 2005-2010 Alfresco Software Limited.
 *
 * This file is part of Alfresco
 *
 * Alfresco is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Alfresco is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Alfresco. If not, see <http://www.gnu.org/licenses/>.
 */
package org.alfresco.module.vti.handler;

import org.alfresco.repo.site.SiteDoesNotExistException;
import org.alfresco.service.cmr.dictionary.InvalidTypeException;
import org.alfresco.service.cmr.model.FileNotFoundException;

/**
 * Site list service fundamental API.
 * 
 * @author Nick Burch
 */
public interface ListServiceHandler
{
    /**
     * Creates a Data List of the given type
     */
    public void createList(String listName, String description, String dws, int templateId)
       throws SiteDoesNotExistException, InvalidTypeException;

    /**
     * Deletes a Data List
     * 
     * @param url The site-based URL of the folder to delete
     */
    public void deleteList(String listName, String dws) 
       throws SiteDoesNotExistException, FileNotFoundException;
}

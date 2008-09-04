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
package org.alfresco.cmis.dictionary;

import java.util.Collection;
import java.util.HashMap;

import org.alfresco.error.AlfrescoRuntimeException;
import org.alfresco.model.ContentModel;
import org.alfresco.service.cmr.dictionary.AssociationDefinition;
import org.alfresco.service.cmr.dictionary.DataTypeDefinition;
import org.alfresco.service.cmr.dictionary.DictionaryService;
import org.alfresco.service.cmr.dictionary.PropertyDefinition;
import org.alfresco.service.namespace.NamespaceException;
import org.alfresco.service.namespace.NamespaceService;
import org.alfresco.service.namespace.QName;

/**
 * CMIS <-> Alfresco mappings
 * 
 * @author andyh
 */
public class CMISMapping
{
    /**
     * The Alfresco CMIS model URI.
     */
    public static String CMIS_MODEL_URI = "http://www.alfresco.org/model/cmis/0.44";

    /**
     * The Alfresco CMIS Model name.
     */
    public static String CMIS_MODEL_NAME = "cmismodel";

    /**
     * The QName for the Alfresco CMIS Model.
     */
    public static QName CMIS_MODEL_QNAME = QName.createQName(CMIS_MODEL_URI, CMIS_MODEL_NAME);

    /**
     * Type id for CMIS documents, from the spec.
     */
    public static String DOCUMENT_OBJECT_TYPE = "DOCUMENT_OBJECT_TYPE";

    /**
     * Type is for CMIS folders, from the spec.
     */
    public static String FOLDER_OBJECT_TYPE = "FOLDER_OBJECT_TYPE";

    /**
     * Type Id for CMIS Relationships, from the spec.
     */
    public static String RELATIONSHIP_OBJECT_TYPE = "RELATIONSHIP_OBJECT_TYPE";

    /**
     * QName for CMIS documents in the Alfresco CMIS model.
     */
    public static QName DOCUMENT_QNAME = QName.createQName(CMIS_MODEL_URI, DOCUMENT_OBJECT_TYPE);

    /**
     * QName for CMIS folders in the Alfresco CMIS model
     */
    public static QName FOLDER_QNAME = QName.createQName(CMIS_MODEL_URI, FOLDER_OBJECT_TYPE);

    /**
     * QName for CMIS relationships in the the Alfresco CMIS model.
     */
    public static QName RELATIONSHIP_QNAME = QName.createQName(CMIS_MODEL_URI, RELATIONSHIP_OBJECT_TYPE);

    public static CMISTypeId DOCUMENT_TYPE_ID = new CMISTypeId(CMISScope.DOCUMENT, DOCUMENT_QNAME, DOCUMENT_OBJECT_TYPE);

    public static CMISTypeId FOLDER_TYPE_ID = new CMISTypeId(CMISScope.FOLDER, FOLDER_QNAME, FOLDER_OBJECT_TYPE);

    public static CMISTypeId RELATIONSHIP_TYPE_ID = new CMISTypeId(CMISScope.RELATIONSHIP, RELATIONSHIP_QNAME, RELATIONSHIP_OBJECT_TYPE);

    // CMIS properties

    public static String PROP_OBJECT_ID = "OBJECT_ID";

    public static String PROP_URI = "URI";

    public static String PROP_OBJECT_TYPE_ID = "OBJECT_TYPE_ID";

    public static String PROP_CREATED_BY = "CREATED_BY";

    public static String PROP_CREATION_DATE = "CREATION_DATE";

    public static String PROP_LAST_MODIFIED_BY = "LAST_MODIFIED_BY";

    public static String PROP_LAST_MODIFICATION_DATE = "LAST_MODIFICATION_DATE";

    public static String PROP_CHANGE_TOKEN = "CHANGE_TOKEN";

    public static String PROP_NAME = "NAME";

    public static String PROP_IS_IMMUTABLE = "IS_IMMUTABLE";

    public static String PROP_IS_LATEST_VERSION = "IS_LATEST_VERSION";

    public static String PROP_IS_MAJOR_VERSION = "IS_MAJOR_VERSION";

    public static String PROP_IS_LATEST_MAJOR_VERSION = "IS_LATEST_MAJOR_VERSION";

    public static String PROP_VERSION_LABEL = "VERSION_LABEL";

    public static String PROP_VERSION_SERIES_ID = "VERSION_SERIES_ID";

    public static String PROP_VERSION_SERIES_IS_CHECKED_OUT = "VERSION_SERIES_IS_CHECKED_OUT";

    public static String PROP_VERSION_SERIES_CHECKED_OUT_BY = "VERSION_SERIES_CHECKED_OUT_BY";

    public static String PROP_VERSION_SERIES_CHECKED_OUT_ID = "VERSION_SERIES_CHECKED_OUT_ID";

    public static String PROP_CHECKIN_COMMENT = "CHECKIN_COMMENT";

    public static String PROP_CONTENT_STREAM_ALLOWED = "CONTENT_STREAM_ALLOWED";

    public static String PROP_CONTENT_STREAM_LENGTH = "CONTENT_STREAM_LENGTH";

    public static String PROP_CONTENT_STREAM_MIME_TYPE = "CONTENT_STREAM_MIME_TYPE";

    public static String PROP_CONTENT_STREAM_FILENAME = "CONTENT_STREAM_FILENAME";

    public static String PROP_CONTENT_STREAM_URI = "CONTENT_STREAM_URI";

    public static String PROP_PARENT = "PARENT";

    public static String PROP_ALLOWED_CHILD_OBJECT_TYPES = "ALLOWED_CHILD_OBJECT_TYPES";

    public static String PROP_SOURCE_ID = "SOURCE_ID";

    public static String PROP_TARGET_ID = "TARGET_ID";

    // Mappings
    // - no entry means no mapping and pass through as is

    private static HashMap<QName, CMISTypeId> qNameToCmisTypeId = new HashMap<QName, CMISTypeId>();

    private static HashMap<QName, QName> cmisToAlfrecsoTypes = new HashMap<QName, QName>();

    private static HashMap<QName, QName> alfrescoToCmisTypes = new HashMap<QName, QName>();

    private static HashMap<QName, CMISPropertyType> alfrescoPropertyTypesToCimsPropertyTypes = new HashMap<QName, CMISPropertyType>();

    /**
     * Set up mappings
     */
    static
    {
        qNameToCmisTypeId.put(DOCUMENT_QNAME, DOCUMENT_TYPE_ID);
        qNameToCmisTypeId.put(FOLDER_QNAME, FOLDER_TYPE_ID);
        qNameToCmisTypeId.put(RELATIONSHIP_QNAME, RELATIONSHIP_TYPE_ID);

        cmisToAlfrecsoTypes.put(DOCUMENT_QNAME, ContentModel.TYPE_CONTENT);
        cmisToAlfrecsoTypes.put(FOLDER_QNAME, ContentModel.TYPE_FOLDER);
        cmisToAlfrecsoTypes.put(RELATIONSHIP_QNAME, null);

        alfrescoToCmisTypes.put(ContentModel.TYPE_CONTENT, DOCUMENT_QNAME);
        alfrescoToCmisTypes.put(ContentModel.TYPE_FOLDER, FOLDER_QNAME);

        alfrescoPropertyTypesToCimsPropertyTypes.put(DataTypeDefinition.ANY, null);
        alfrescoPropertyTypesToCimsPropertyTypes.put(DataTypeDefinition.ASSOC_REF, null);
        alfrescoPropertyTypesToCimsPropertyTypes.put(DataTypeDefinition.BOOLEAN, CMISPropertyType.Boolean);
        alfrescoPropertyTypesToCimsPropertyTypes.put(DataTypeDefinition.CATEGORY, CMISPropertyType.ID);
        alfrescoPropertyTypesToCimsPropertyTypes.put(DataTypeDefinition.CHILD_ASSOC_REF, null);
        alfrescoPropertyTypesToCimsPropertyTypes.put(DataTypeDefinition.CONTENT, null);
        alfrescoPropertyTypesToCimsPropertyTypes.put(DataTypeDefinition.DATE, CMISPropertyType.DateTime);
        alfrescoPropertyTypesToCimsPropertyTypes.put(DataTypeDefinition.DATETIME, CMISPropertyType.DateTime);
        alfrescoPropertyTypesToCimsPropertyTypes.put(DataTypeDefinition.DOUBLE, CMISPropertyType.Decimal);
        alfrescoPropertyTypesToCimsPropertyTypes.put(DataTypeDefinition.FLOAT, CMISPropertyType.Decimal);
        alfrescoPropertyTypesToCimsPropertyTypes.put(DataTypeDefinition.INT, CMISPropertyType.Integer);
        alfrescoPropertyTypesToCimsPropertyTypes.put(DataTypeDefinition.LOCALE, null);
        alfrescoPropertyTypesToCimsPropertyTypes.put(DataTypeDefinition.LONG, CMISPropertyType.Integer);
        alfrescoPropertyTypesToCimsPropertyTypes.put(DataTypeDefinition.MLTEXT, CMISPropertyType.String);
        alfrescoPropertyTypesToCimsPropertyTypes.put(DataTypeDefinition.NODE_REF, CMISPropertyType.ID);
        alfrescoPropertyTypesToCimsPropertyTypes.put(DataTypeDefinition.PATH, null);
        alfrescoPropertyTypesToCimsPropertyTypes.put(DataTypeDefinition.QNAME, null);
        alfrescoPropertyTypesToCimsPropertyTypes.put(DataTypeDefinition.TEXT, CMISPropertyType.String);
    }

    private DictionaryService dictionaryService;

    private NamespaceService namespaceService;

    /**
     * Set the dictionary Service
     * 
     * @param dictionaryService
     */
    public void setDictionaryService(DictionaryService dictionaryService)
    {
        this.dictionaryService = dictionaryService;
    }

    /**
     * Gets the dictionary service
     * 
     * @return dictionaryService
     */
    /* package */DictionaryService getDictionaryService()
    {
        return this.dictionaryService;
    }

    /**
     * Set the namespace service
     * 
     * @param namespaceService
     */
    public void setNamespaceService(NamespaceService namespaceService)
    {
        this.namespaceService = namespaceService;
    }

    /**
     * Gets the namespace service
     * 
     * @return namespaceService
     */
    /* package */NamespaceService getNamespaceService()
    {
        return this.namespaceService;
    }

    /**
     * Id this a CMIS core type defined in the Alfresco CMIS model
     * 
     * @param typeQName
     * @return
     */
    public boolean isCmisCoreType(QName typeQName)
    {
        return qNameToCmisTypeId.get(typeQName) != null;
    }

    /**
     * Gets the CMIS Type Id given the serialized type Id
     * 
     * @param typeId
     *            type id in the form of <ROOT_TYPE_ID>/<PREFIX>_<LOCALNAME>
     * @return
     */
    public CMISTypeId getCmisTypeId(String typeId)
    {
        // Is it a CMIS root object type id?
        if (typeId.equals(DOCUMENT_TYPE_ID.getTypeId()))
        {
            return DOCUMENT_TYPE_ID;
        }
        else if (typeId.equals(FOLDER_TYPE_ID.getTypeId()))
        {
            return FOLDER_TYPE_ID;
        }
        else if (typeId.equals(RELATIONSHIP_TYPE_ID.getTypeId()))
        {
            return RELATIONSHIP_TYPE_ID;
        }
        // TODO: Policy root object type

        // Is it an Alfresco type id?
        if (typeId.length() < 4 || typeId.charAt(1) != '/')
        {
            throw new AlfrescoRuntimeException("Malformed type id '" + typeId + "'");
        }

        // Alfresco type id
        CMISScope scope = CMISScope.toScope(typeId.charAt(0));
        if (scope == null)
        {
            throw new AlfrescoRuntimeException("Malformed type id '" + typeId + "'; discriminator " + typeId.charAt(0) + " unknown");
        }
        QName typeQName = QName.resolveToQName(namespaceService, typeId.substring(2).replace('_', ':'));

        // Construct CMIS Type Id
        return new CMISTypeId(scope, typeQName, typeId);
    }

    /**
     * Gets the CMIS Type Id given the Alfresco QName for the type in any Alfresco model
     * 
     * @param typeQName
     * @return
     */
    public CMISTypeId getCmisTypeId(CMISScope scope, QName typeQName)
    {
        CMISTypeId typeId = qNameToCmisTypeId.get(typeQName);
        if (typeId == null)
        {
            StringBuilder builder = new StringBuilder(128);
            builder.append(scope.discriminator());
            builder.append("/");
            builder.append(buildPrefixEncodedString(typeQName, false));
            return new CMISTypeId(scope, typeQName, builder.toString());
        }
        else
        {
            return typeId;
        }
    }

    public CMISTypeId getCmisTypeId(QName typeQName)
    {
        if (isValidCmisDocument(typeQName))
        {
            return getCmisTypeId(CMISScope.DOCUMENT, getCmisType(typeQName));
        }
        else if (isValidCmisFolder(typeQName))
        {
            return getCmisTypeId(CMISScope.FOLDER, getCmisType(typeQName));
        }
        else if (typeQName.equals(CMISMapping.RELATIONSHIP_QNAME))
        {
            return getCmisTypeId(CMISScope.RELATIONSHIP, getCmisType(typeQName));
        }
        else if (typeQName.equals(ContentModel.TYPE_CONTENT))
        {
            return getCmisTypeId(CMISScope.DOCUMENT, getCmisType(typeQName));
        }
        else if (typeQName.equals(ContentModel.TYPE_FOLDER))
        {
            return getCmisTypeId(CMISScope.FOLDER, getCmisType(typeQName));
        }
        else
        {
            return null;
        }

    }

    /**
     * Get the query name for Alfresco qname
     * 
     * @param namespaceService
     * @param typeQName
     * @return
     */
    public String getQueryName(QName typeQName)
    {
        return buildPrefixEncodedString(typeQName, true);
    }

    private String buildPrefixEncodedString(QName qname, boolean upperCase)
    {
        StringBuilder builder = new StringBuilder(128);

        if (!qname.getNamespaceURI().equals(CMIS_MODEL_URI))
        {
            Collection<String> prefixes = namespaceService.getPrefixes(qname.getNamespaceURI());
            if (prefixes.size() == 0)
            {
                throw new NamespaceException("A namespace prefix is not registered for uri " + qname.getNamespaceURI());
            }
            String resolvedPrefix = prefixes.iterator().next();

            builder.append(upperCase ? resolvedPrefix.toUpperCase() : resolvedPrefix);
            builder.append("_");
        }

        builder.append(upperCase ? qname.getLocalName().toUpperCase() : qname.getLocalName());
        return builder.toString();
    }

    /**
     * Is this a valid CMIS type The type must be a core CMIS type or extend cm:content or cm:folder The alfresco types
     * cm:content and cm:folder are hidden by the CMIS types
     * 
     * @param dictionaryService
     * @param typeQName
     * @return
     */
    public boolean isValidCmisType(QName typeQName)
    {
        return isValidCmisFolder(typeQName) || isValidCmisDocument(typeQName) || isValidCmisRelationship(typeQName);
    }

    /**
     * Is this a valid cmis document or folder type (not a relationship)
     * 
     * @param dictionaryService
     * @param typeQName
     * @return
     */
    public boolean isValidCmisDocumentOrFolder(QName typeQName)
    {
        return isValidCmisFolder(typeQName) || isValidCmisDocument(typeQName);
    }

    /**
     * Is this a valid CMIS folder type?
     * 
     * @param dictionaryService
     * @param typeQName
     * @return
     */
    public boolean isValidCmisFolder(QName typeQName)
    {
        if (typeQName == null)
        {
            return false;
        }
        if (typeQName.equals(FOLDER_QNAME))
        {
            return true;
        }

        if (dictionaryService.isSubClass(typeQName, ContentModel.TYPE_FOLDER))
        {
            if (typeQName.equals(ContentModel.TYPE_FOLDER))
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Is this a valid CMIS document type?
     * 
     * @param dictionaryService
     * @param typeQName
     * @return
     */
    public boolean isValidCmisDocument(QName typeQName)
    {
        if (typeQName == null)
        {
            return false;
        }
        if (typeQName.equals(DOCUMENT_QNAME))
        {
            return true;
        }

        if (dictionaryService.isSubClass(typeQName, ContentModel.TYPE_CONTENT))
        {
            if (typeQName.equals(ContentModel.TYPE_CONTENT))
            {
                return false;
            }
            else
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Is an association valid in CMIS? It must be a non-child relationship and the source and target must both be valid
     * CMIS types.
     * 
     * @param dictionaryService
     * @param associationQName
     * @return
     */
    public boolean isValidCmisRelationship(QName associationQName)
    {
        if (associationQName == null)
        {
            return false;
        }
        if (associationQName.equals(RELATIONSHIP_QNAME))
        {
            return true;
        }
        AssociationDefinition associationDefinition = dictionaryService.getAssociation(associationQName);
        if (associationDefinition == null)
        {
            return false;
        }
        if (associationDefinition.isChild())
        {
            return false;
        }
        if (!isValidCmisDocumentOrFolder(getCmisType(associationDefinition.getSourceClass().getName())))
        {
            return false;
        }
        if (!isValidCmisDocumentOrFolder(getCmisType(associationDefinition.getTargetClass().getName())))
        {
            return false;
        }
        return true;
    }

    /**
     * Given an Alfresco model type map it to the appropriate type. Maps cm:folder and cm:content to the CMIS
     * definitions
     * 
     * @param typeQName
     * @return
     */
    public QName getCmisType(QName typeQName)
    {
        QName mapped = alfrescoToCmisTypes.get(typeQName);
        if (mapped != null)
        {
            return mapped;
        }
        return typeQName;
    }
    
    /**
     * Given a CMIS model type map it to the appropriate Alfresco type.
     * 
     * @param cmisTypeQName
     * @return
     */
    public QName getAlfrescoType(QName cmisTypeQName)
    {
        QName mapped = cmisToAlfrecsoTypes.get(cmisTypeQName);
        if (mapped != null)
        {
            return mapped;
        }
        return cmisTypeQName;
    }

    /**
     * Get the CMIS property name from the property QName.
     * 
     * @param namespaceService
     * @param propertyQName
     * @return
     */
    public String getCmisPropertyName(QName propertyQName)
    {
        return buildPrefixEncodedString(propertyQName, true);
    }

    /**
     * Get the CMIS property type for a property
     * 
     * @param dictionaryService
     * @param propertyQName
     * @return
     */
    public CMISPropertyType getPropertyType(QName propertyQName)
    {
        PropertyDefinition propertyDefinition = dictionaryService.getProperty(propertyQName);
        DataTypeDefinition dataTypeDefinition;
        if (propertyDefinition != null)
        {
            dataTypeDefinition = propertyDefinition.getDataType();
        }
        else
        {
            dataTypeDefinition = dictionaryService.getDataType(propertyQName);
        }

        QName dQName = dataTypeDefinition.getName();
        if (propertyQName.getNamespaceURI().equals(CMIS_MODEL_URI))
        {
            if (dQName.equals(DataTypeDefinition.QNAME) || dQName.equals(DataTypeDefinition.NODE_REF))
            {
                return CMISPropertyType.ID;
            }
            else
            {
                alfrescoPropertyTypesToCimsPropertyTypes.get(dQName);
            }
        }
        return alfrescoPropertyTypesToCimsPropertyTypes.get(dQName);

    }

    /**
     * Lookup a CMIS property name and get the Alfresco property QName
     * 
     * @param dictionaryService
     * @param namespaceService
     * @param cmisPropertyName
     * @return
     */
    public QName getPropertyQName(String cmisPropertyName)
    {
        // Try the cmis model first - it it matches we are done
        QName cmisPropertyQName = QName.createQName(CMIS_MODEL_URI, cmisPropertyName);
        if (dictionaryService.getProperty(cmisPropertyQName) != null)
        {
            return cmisPropertyQName;
        }

        // Find prefix and property name - in upper case

        int split = cmisPropertyName.indexOf('_');
        String prefix = cmisPropertyName.substring(0, split);
        String localName = cmisPropertyName.substring(split + 1);

        // Try lower case version first.

        QName propertyQName = QName.createQName(prefix.toLowerCase(), localName.toLowerCase(), namespaceService);
        if (dictionaryService.getProperty(propertyQName) != null)
        {
            return propertyQName;
        }

        // Full case insensitive hunt

        for (String test : namespaceService.getPrefixes())
        {
            if (test.equalsIgnoreCase(prefix))
            {
                prefix = test;
                break;
            }
        }
        String uri = namespaceService.getNamespaceURI(prefix);

        for (QName qname : dictionaryService.getAllProperties(null))
        {
            if (qname.getNamespaceURI().equals(uri))
            {
                if (qname.getLocalName().equalsIgnoreCase(localName))
                {
                    return qname;
                }
            }
        }

        return null;

    }

    /**
     * @param tableName
     * @return
     */
    public QName getAlfrescoClassQNameFromCmisTableName(String tableName)
    {
        if (tableName.equals(DOCUMENT_TYPE_ID.getTypeId()))
        {
            return ContentModel.TYPE_CONTENT;
        }
        else if (tableName.equals(FOLDER_TYPE_ID.getTypeId()))
        {
            return ContentModel.TYPE_FOLDER;
        }
        else if (tableName.equals(RELATIONSHIP_TYPE_ID.getTypeId()))
        {
            return null;
        }

        // Find prefix and property name - in upper case

        int split = tableName.indexOf('_');
        String prefix = tableName.substring(0, split);
        String localName = tableName.substring(split + 1);

        // Try lower case version first.

        QName classQName = QName.createQName(prefix.toLowerCase(), localName.toLowerCase(), namespaceService);
        if (dictionaryService.getClass(classQName) != null)
        {
            return classQName;
        }

        // Full case insensitive hunt

        for (String test : namespaceService.getPrefixes())
        {
            if (test.equalsIgnoreCase(prefix))
            {
                prefix = test;
                break;
            }
        }
        String uri = namespaceService.getNamespaceURI(prefix);

        for (QName qname : dictionaryService.getAllTypes())
        {
            if (qname.getNamespaceURI().equals(uri))
            {
                if (qname.getLocalName().equalsIgnoreCase(localName))
                {
                    return qname;
                }
            }
        }

        for (QName qname : dictionaryService.getAllAspects())
        {
            if (qname.getNamespaceURI().equals(uri))
            {
                if (qname.getLocalName().equalsIgnoreCase(localName))
                {
                    return qname;
                }
            }
        }

        return null;

    }

    /**
     * @param namespaceService
     * @param propertyQName
     * @return
     */
    public String getCmisPropertyId(QName propertyQName)
    {
        if (propertyQName.getNamespaceURI().equals(CMIS_MODEL_URI))
        {
            return propertyQName.getLocalName();
        }
        else
        {
            return propertyQName.toString();

        }
    }
}

<#import "/org/alfresco/modules/discussions/user.lib.ftl" as userLib/>

<#--
  Renders a topic.
  
  @param topic The topic data to render.
  
  ${page.url.context}
  ${url.context}
-->


<#macro topicViewHTML htmlid topic>
<div id="${topic.name}" class="node topic topicview">

   <div class="nodeEdit">
      <#if (topic.permissions.reply)>
      <div class="onAddReply" id="${htmlid}-onAddReply-${topic.name}">
         <a href="#" class="topic-action-link">${msg("topic.action.addReply")}</a>
      </div>
      </#if>
      <#if (topic.permissions.edit)>
      <div class="onEditNode" id="${htmlid}-onEditNode-${topic.name}">
         <a href="#" class="topic-action-link">${msg("topic.action.edit")}</a>
      </div>
      </#if>
      <#if (topic.permissions.delete)>
      <div class="onDeleteNode" id="${htmlid}-onDeleteNode-${topic.name}">
         <a href="#" class="topic-action-link">${msg("topic.action.delete")}</a>
      </div>
      </#if>
   </div>
  
   <div class="authorPicture">
      <@userLib.renderAvatarImage user=topic.author />
   </div>

   <div class="nodeContent">
      <div class="nodeTitle">
         <a href="discussions-topicview?topicId=${topic.name}">
            ${topic.title?html}
         </a>
         <#if topic.isUpdated><span class="nodeStatus">(${msg("topic.updated")})</span></#if>
      </div>
      <div class="published">
         <span class="nodeAttrLabel">${msg("topic.info.createdOn")}:</span> <span class="nodeAttrValue"> ${topic.createdOn?datetime?string.medium_short}</span>
         <span class="spacer"> | </span>
         <span class="nodeAttrLabel">${msg("topic.info.author")}:</span><span class="nodeAttrValue"><@userLib.renderUserLink user=topic.author /></span>      
         <br />
         <#if topic.lastReplyBy??>
            <span class="nodeAttrLabel">${msg("topic.info.lastReplyBy")}:</span> <span class="nodeAttrValue"><@userLib.renderUserLink user=topic.lastReplyBy /></span>
            <span class="spacer"> | </span>
            <span class="nodeAttrLabel">${msg("topic.info.lastReplyOn")}:</span> <span class="nodeAttrValue"><#if topic.lastReplyOn??>${topic.lastReplyOn?datetime?string.medium_short}</#if></span>
         <#else>
            <span class="nodeAttrLabel">${msg("topic.footer.replies")}:</span> <span class="nodeAttrValue">${msg("topic.info.noReplies")}</span>
         </#if>
      </div>
      
      <div class="userLink"><@userLib.renderUserLink user=topic.author /> ${msg("topic.said")}:</div>
      <div class="content yuieditor">${topic.content}</div>
   </div>
   <div class="nodeFooter">
      <span class="nodeFooterBloc">
         <span class="nodeAttrLabel replyTo">${msg("topic.footer.replies")}:</span><span class="nodeAttrValue"> (${topic.replyCount})</span>
      </span> 
      
      <span class="spacer"> | </span>

      <#if (topic.tags?size > 0)>
      <span class="nodeFooterBloc">
         <span class="nodeAttrLabel tag">${msg("topic.tags")}:</span>
         <#list topic.tags as tag>
            <span class="nodeAttrValue" id="${htmlid}-onTagSelection-${tag}">
               <a href="#" class="tag-link-span">${tag?html}</a>
            </span><#if tag_has_next> , </#if> 
         </#list>
      </span>
      </#if> 
   </div>
</div>
</#macro>


<#--
  Renders a form to edit a topic.
  
  @param form-id The form id to use
  @param topic The topic data to insert into the form.
               Can be empty in which case the form will contain no data.
-->
<#macro topicFormHTML htmlid topic="">
<div class="editNodeForm">
   <form id="${htmlid}-form" method="post"
      <#if topic?has_content>
         action="${url.serviceContext}/modules/discussions/topic/update-topic"
      <#else>
         action="${url.serviceContext}/modules/discussions/topic/create-topic"
      </#if>
   >
      <div>
         <input type="hidden" name="site" value="${site}" />
         <input type="hidden" name="container" value="${container}" />
         <input type="hidden" name="browseTopicUrl" value="${url.context}/page/site/${site}/discussions-topicview?container=${container}&amp;topicId={post.name}" />
         <input type="hidden" name="htmlid" value="${htmlid}" />
         <#if topic?has_content>
            <input type="hidden" name="topicId" value="${topic.name}" />
         </#if>
         <#if topic?has_content>
            <#assign tags=topic.tags />
         <#else>
            <#assign tags=[] />
         </#if>
         <#import "/org/alfresco/modules/taglibrary/taglibrary.lib.ftl" as taglibraryLib/>
         <@taglibraryLib.renderTagInputs htmlid=htmlid tags=tags tagInputName="tags" />
         
         <#-- title -->
         <label>${msg("topic.form.topicTitle")}:</label>
         <input type="text" value="<#if topic?has_content && topic.title?has_content>${topic.title?html}</#if>"
                name="title" id="${htmlid}-title" size="80" />
                
         <#-- content -->
         <label>${msg("topic.form.topicText")}:</label>
         <textarea rows="8" cols="80" name="content" id="${htmlid}-content" class="yuieditor"
            ><#if topic?has_content && topic.content?has_content>${topic.content?html}</#if></textarea> 
         
         <#-- tags -->
         <label>${msg("topic.tags")}:</label>
         <@taglibraryLib.renderTagLibrary htmlid=htmlid site=site tags=tags />

      </div>
      <div class="nodeFormAction">
         <input type="submit" id="${htmlid}-ok-button" value="<#if topic?has_content>${msg('topic.form.save')}<#else>${msg('topic.form.create')}</#if>" />
         <input type="reset" id="${htmlid}-cancel-button" value="${msg('topic.form.cancel')}" />
      </div>
   </form>
</div>
</#macro>

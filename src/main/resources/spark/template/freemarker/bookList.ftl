<#if hasNoBooks??>
<div class="starter-template">
	<h1>${hasNoBooks}</h1>
</div>
<#else>
<div class="starter-template">
	<#list books as book>
	<h3>${book.getTitle()}</h3>
	<h4>${book.getCreatedAt()}</h4>
	<h4>${book.getSummaryLink()}</h4>
	<h4>${book.getEditLink()} | ${book.getDeleteLink()}</h4>
	</#list>
</div>
</#if>

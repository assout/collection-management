<div class="starter-template">
	<form class="form-horizontal" role="form" id='book-create-form' method='POST'
	 <#if book??>action="/book/update/:id"<#else>action="/book/create"</#if>>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="title">Title: </label>
			<div class="col-sm-5">
				<input class="form-control" type='text' id="title" name='book-title' placeholder="Enter a new title" <#if book??>value="${book.getTitle()}"</#if> />
			</div>
		</div>
		<div class="form-group">
			<label class="col-sm-3 control-label" for="summary">Summary: </label>
			<div class="col-sm-5">
				<input class="form-control" type='text' id="summary" name='book-summary' placeholder="Enter a new summary" <#if book??>value="${book.getSummary()}"</#if> />
			</div>
		</div>
		<#if book??>
		<input type='hidden' name='book-id' value="${book.getId()}" />
		</#if>
	</form>

	<label for="content">Content</label>
	<textarea class="form-control" name='book-content' id="content" rows='4' cols='50' form='book-create-form' placeholder="Enter book content"><#if book??>${book.getContent()}</#if></textarea>
	<input type='submit' 
	<#if book??>value='Update'<#else>value='Publish'</#if>
	class="btn btn-primary" form='book-create-form' />
</div>

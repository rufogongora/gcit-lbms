
<jsp:include page="../Header.jsp"/>
 
 
     <div class="filler">
     
    </div><!-- /.carousel -->
 

    <!-- Marketing messaging and featurettes
    ================================================== -->
    <!-- Wrap the rest of the page in another container to center all the content. -->
    <div class="container marketing">
		<div class = "message">
			<h2> ${result}</h2>	
		</div>
      <!-- Three columns of text below the carousel -->
      <div class="row">
        <div class="col-lg-4">
          <a href="#" data-toggle="modal" data-target="#authorModal" ><img class="img-circle" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" alt="Generic placeholder image" width="140" height="140">
         	</a>
          <h2>Author Menu</h2>
        </div><!-- /.col-lg-4 -->
        <div class="col-lg-4">
        <a href="#" data-toggle="modal" data-target="#deleteAuthorModal" >
          <img class="img-circle" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" alt="Generic placeholder image" width="140" height="140">
          </a> 
          <h2>Heading</h2>
         
         </div><!-- /.col-lg-4 -->
        <div class="col-lg-4">
          <img class="img-circle" src="data:image/gif;base64,R0lGODlhAQABAIAAAHd3dwAAACH5BAAAAAAALAAAAAABAAEAAAICRAEAOw==" alt="Generic placeholder image" width="140" height="140">
          <h2>Heading</h2>
	      </div><!-- /.col-lg-4 -->
      </div><!-- /.row -->
      </div>
      
      
<%@include file = "/modals/modalsInclude.jsp"%>
      

<jsp:include page="../Footer.jsp"/>

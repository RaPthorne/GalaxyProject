<p>Ti sei loggato con successo!</p>

<%

String stato = (String) request.getAttribute("stato");


%>

<p>Inserisci il tuo comando:</p>
<form action="ControllerServlet" method="post">
<input type="text" name="comando" style="width: 400px;"><br>
<input type="submit" value="invia">
<input type="hidden" name="stato" value="<%=stato%>" />
</form>

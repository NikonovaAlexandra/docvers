<%@ page import="java.io.StringWriter" %>
<%@ page import="org.eclipse.mylyn.wikitext.core.parser.builder.HtmlDocumentBuilder" %>
<%@ page import="org.eclipse.mylyn.wikitext.core.parser.MarkupParser" %>
<%@ page import="org.eclipse.mylyn.wikitext.confluence.core.ConfluenceLanguage" %>
<%--
  Created by IntelliJ IDEA.
  User: gosha
  Date: 3/5/13
  Time: 8:37 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<body>
<link rel="stylesheet" href="wiki.css" type="text/css" />

<%
   StringWriter writer = new StringWriter();


   HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);

   // avoid the <html> and <body> tags
   builder.setEmitAsDocument(false);

   MarkupParser parser = new MarkupParser(new ConfluenceLanguage());
   parser.setBuilder(builder);

   parser.parse("h2. Headers i DataLoader\n" +
                " main class : com.siemens.modias.loader.hfdheader.HeaderParser\n" +
                "\n" +
                "\n" +
                "|| Header Name || (+) || (-) || Notes ||\n" +
                "| @HFDVERSION | values | null/empty | error handling works incorrect |\n" +
                "| @CONTENTTYPE | | | |\n" +
                "| @INTERFACE | | | |\n" +
                "| @ENCODING | | | |\n" +
                "| @SOURCESYSTEM | | | |\n" +
                "| @SOURCESYSTEMDATASOURCE | | | |\n" +
                "| @SOURCESYSTEMPROGRAM | | | |\n" +
                "| @SOURCESYSTEMENCODING| | | |\n" +
                "| @EXPORTDATETIME | | | |\n" +
                "| @FIELDNAMES | | | |\n");

   String htmlContent = writer.toString();

 %>
 <%=htmlContent%>
</body>
</html>
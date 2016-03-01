<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<html>
<head lang="zh-cn">
    <meta charset="UTF-8">
    </head>
<body>
<h2>Hello World!</h2>
        <form action="data/file" method="post" enctype="multipart/form-data">
            <input type="file" name="data_file" id="data_file" />
            <input type="text" name="sheetNo" id="sheetNo" />
            <br/>
            <button type="submit" class="btn btn-primary">提交</button>
        </form>
</body>
</html>

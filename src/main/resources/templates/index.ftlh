<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <link rel="stylesheet" href="/js/style.css">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <title>Hill cipher</title>
</head>
<body class="container">
<form id="read-file">
    <div class="container">
        <div id="attach-file">
            <label for="file" class="form-label">Выберите файл</label>
            <input type="file" class="form-control my-2" name="file" accept=".txt"/>
        </div>
    </div>
    <button type="submit" class="btn btn-outline-dark my-2">Подтвердить</button>
</form>

<h1>Ввод матрицы ключа для шифра Хилла</h1>
<button id="btn2" class="btn btn-outline-dark">2x2</button>
<button id="btn3" class="btn btn-outline-dark">3x3</button>
<button id="btn4" class="btn btn-outline-dark">4x4</button>
<button id="btn5" class="btn btn-outline-dark">5x5</button>
<button id="btn6" class="btn btn-outline-dark">6x6</button>

<div id="matrixInputs" style="display: none;">
    <h2>Матрица ключа:</h2>
    <div id="matrix">

        <!-- Инпуты для матрицы будут отображены здесь -->
    </div>
    <button onclick="getRandomValues()" class="btn btn-outline-dark">Рандомные значения</button>
</div>
<div class="textarea-block">
    <div>
        <label for="text-original" class="form-label">Оригинал текста</label>
        <textarea id="text-original" class="form-control textarea-input"></textarea>
    </div>
    <div class="buttons">
        <div class="buttons">
            <button onclick="encode()" class="btn btn-outline-dark my-2">Зашифровать</button>
            <button onclick="decode()" class="btn btn-outline-dark my-2">Расшифровать</button>
            <button onclick="decodeWithText()" class="btn btn-outline-dark my-2">Найти ключ</button>
        </div>
    </div>
    <div>
        <label for="text-encoding" class="form-label">Зашифрованный текст</label>
        <textarea id="text-encoding" class="form-control textarea-input"></textarea>
    </div>

</div>
<div>
    <div id="tables">

    </div>

    <div id="graph">

    </div>
</div>
<canvas id="myChart" width="400" height="200"></canvas>


<script src="js/script.js"></script>
</body>
</html>
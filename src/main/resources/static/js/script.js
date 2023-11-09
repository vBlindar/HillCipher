const readFile = document.getElementById("read-file");
let matrix = [[],[],[],[],[]];
let inverseMatrix = null;
readFile.addEventListener('submit', getTextFromFile);

async function getTextFromFile(e) {
    e.preventDefault();
    const data = new FormData(e.target);
    const response = await fetch("/read-file", {
        method: 'POST',
        body: data
    });
    const text = await response.text();
    const originalText = document.getElementById("text-original");
    originalText.value = text;
}


async function encode(){
    const text = encodeURIComponent(document.getElementById('text-original').value);
    let encodeInput = document.getElementById('text-encoding');
    rewriteMatrix();
    const matrixData = encodeURIComponent(JSON.stringify(matrix)); // Преобразовать матрицу в JSON строку
    const response = await fetch(`/encode?message=${text}&matrixData=${matrixData}`);
    if(response.ok){
        encodeInput.value=await response.text();
    }else{
        const errorMessage = await response.text();
        alert(errorMessage);
    }
    await getFrequencyAndGraph(text);
}
async function getFrequencyAndGraph(text){
    const cipher = encodeURIComponent(document.getElementById('text-encoding').value);
    await getTables(cipher);
    await getMaps(cipher);

}

async function decode(){
    const encodeInput = document.getElementById('text-encoding');
    const encodeText = encodeURIComponent(encodeInput.value);

    const matrixData = encodeURIComponent(JSON.stringify(matrix));
    const response = await fetch(`/decode?message=${encodeText}&matrixData=${matrixData}`);

    encodeInput.value = await response.text();

}


async function decodeWithText(){
    const cipher = encodeURIComponent(document.getElementById('text-encoding').value);
    const decipher =encodeURIComponent(document.getElementById('text-original').value);
    const response = await fetch(`/decode-with-text?cipher=${cipher}&decipher=${decipher}&size=${selectedMatrixSize}`);
    const data = await response.json();
    matrix = data;
    fillRandomValues();

}

async function getTables(text){
    const response = await fetch(`/frequency-table?input=${text}`);
    const tables = document.getElementById('tables');
    tables.innerHTML = await response.text();

}

/////////// frequency

let myChart;
async function getMaps(text) {
    const response = await fetch(`/frequency-maps?input=${text}`);
    const maps = await response.json();
    const textFrequencyMap = maps.decodeAlphabet;
    const languageFrequencyMap = maps.originalAlphabet;

// Создаем объекты JavaScript для данных о частоте текста и частоте языка
    const textFrequency = {};
    const languageFrequency = {};

// Заполняем объекты данными из Map
    for (const [key, value] of Object.entries(textFrequencyMap)) {
        textFrequency[key] = value;
    }

    for (const [key, value] of Object.entries(languageFrequencyMap)) {
        languageFrequency[key] = value;
    }
    // Сортируем коллекции по значению
    const sortedTextFrequency = Object.fromEntries(
        Object.entries(textFrequency).sort(([, a], [, b]) => b - a)
    );

    const sortedLanguageFrequency = Object.fromEntries(
        Object.entries(languageFrequency).sort(([, a], [, b]) => b - a)
    );
    const sortedTextFrequencyValues = Object.values(sortedTextFrequency);
    const sortedLanguageFrequencyValues = Object.values(sortedLanguageFrequency);

    const maxLabelsToShow = 37; // Максимальное количество меток для отображения

// Получаем массив меток и ограничиваем его до максимального количества
    const labelsToShow = Object.keys(sortedTextFrequencyValues).slice(0, maxLabelsToShow);
    // Вызываем функцию для создания или обновления графика
    createOrUpdateChart(labelsToShow, sortedTextFrequencyValues, sortedLanguageFrequencyValues);
}

function createOrUpdateChart(labels, textFrequencyValues, languageFrequencyValues) {
    if (myChart) {
        // Если график уже существует, обновляем его данные
        myChart.data.labels = labels;
        myChart.data.datasets[0].data = textFrequencyValues;
        myChart.data.datasets[1].data = languageFrequencyValues;
        myChart.update(); // Обновляем график
    } else {
        // Если график еще не существует, создаем его
        const ctx = document.getElementById('myChart').getContext('2d');
        const data = {
            labels: labels,
            datasets: [
                {
                    label: 'Частота в тексте',
                    data: textFrequencyValues,
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 2
                },
                {
                    label: 'Частота в языке',
                    data: languageFrequencyValues,
                    borderColor: 'rgb(255,99,99)',
                    borderWidth: 2
                }
            ]
        };

        const config = {
            type: 'line',
            data: data,
            options: {
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        };

        myChart = new Chart(ctx, config);
    }
}




////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
const matrixSizeButtons = document.querySelectorAll('button[id^="btn"]');
const matrixInputsContainer = document.getElementById('matrixInputs');
const matrixContainer = document.getElementById('matrix');

let selectedMatrixSize = null;

matrixSizeButtons.forEach(button => {
    button.addEventListener('click', () => {
        selectedMatrixSize = button.id.replace('btn', '');
        createMatrixInputs();
    });
});

function createMatrixInputs() {
    if (selectedMatrixSize) {
        matrixContainer.innerHTML = '';
        for (let i = 0; i < selectedMatrixSize; i++) {
            const row = document.createElement('div');
            for (let j = 0; j < selectedMatrixSize; j++) {
                const input = document.createElement('input');
                input.classList = 'm-2 input-size'
                input.type = 'number';
                input.min='0';
                input.max='36';
                input.id=`${i + 1}${j + 1}`;
                input.placeholder = `(${i + 1},${j + 1})`;
                row.appendChild(input);
            }
            matrixContainer.appendChild(row);
        }
        matrixInputsContainer.style.display = 'block';
    }
}

async function getRandomValues(){
    const response = await fetch(`/random-matrix?size=${selectedMatrixSize}`);
    const data = await response.json();
    matrix = data;
    fillRandomValues();
}

function fillRandomValues(){
    for (let i = 0; i<selectedMatrixSize; i++){
        for  (let j =0; j<selectedMatrixSize; j++){
            let input = document.getElementById(`${i+1}${j+1}`)
            input.value=matrix[i][j];
        }
    }
}

function rewriteMatrix(){
     matrix  = new Array(selectedMatrixSize);
    for (var i = 0; i < selectedMatrixSize; i++) {
        matrix[i] = new Array(selectedMatrixSize);
    }
    for (let i = 0; i<selectedMatrixSize; i++){
        for  (let j =0; j<selectedMatrixSize; j++){
            let input = document.getElementById(`${i+1}${j+1}`)
            matrix[i][j]=input.value;
        }
    }
}

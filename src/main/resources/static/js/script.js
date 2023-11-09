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

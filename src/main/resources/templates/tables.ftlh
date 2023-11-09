<style>
    .div-scroll, table {
        padding: 12px;
        height: 250px;
        width: 350px;
        border-radius: 10px;
        box-shadow: 1px 2px 1px 2px rgb(230, 229, 229);
        margin: 15px;
        border: 1px solid #141313;
        background-color: #cbeaf8;
        overflow-y: scroll;
        overflow-x: hidden;
    }
    tr,th,td{
        max-width: 110px;
    }
</style>
<div class="div-scroll">
    <table class="my-table">
        <tr class="md-2">
            <th>Русский язык</th>
            <th>Текст</th>
        </tr>
        <#list alphabetEntries as entry>
            <tr>
                <td>${entry.originalLetter}</td>
                <td>${entry.decodeLetter!'-'}</td>
            </tr>
        </#list>
    </table>
</div>
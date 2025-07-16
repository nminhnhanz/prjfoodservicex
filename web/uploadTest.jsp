<form action="MenuController" method="post" enctype="multipart/form-data">
    <input type="hidden" name="action" value="testUpload" />

    <label>Food:</label>
    <input type="text" name="food" />
    <br/>

    <label>Image:</label>
    <input type="file" name="FoodImage" />
    <br/>

    <input type="submit" value="Submit" />
</form>

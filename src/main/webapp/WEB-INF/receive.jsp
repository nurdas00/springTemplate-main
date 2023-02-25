<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link
            href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
            rel="stylesheet"
            integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
            crossorigin="anonymous"
    />
    <title>Form</title>
</head>
<body>

<div>
    <div class="row align-items-center" style="height: 100vh;">
        <div class="mx-auto col-10">
            <form id="receiveForm" method="post">
                <h1>Fill form</h1>
                <div class="form-group">
                    <label for="name">Name</label>
                    <input
                            class="form-control confirmation-code"
                            id="name"
                            placeholder="Name..."
                    />
                </div>
                <div class="form-group">
                    <label for="lastname">Lastname</label>
                    <input
                            class="form-control confirmation-code"
                            id="lastname"
                            placeholder="Lastname..."
                    />
                </div>
                <div class="form-group">
                    <label for="confirmation-code">Secret Code</label>
                    <input
                            class="form-control confirmation-code"
                            id="confirmation-code"
                            placeholder="Confirmation code..."
                    />
                </div>
                <button type="submit" class="receiveButton btn btn-primary btn-customized mt-4" form="receiveForm">
                    Ok
                </button>
            </form>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script
        src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

<script>
    function setData(id) {
        const fields = ["name", "lastname", "confirmationCode"];
        let user = {};
        let i = 0;

        $(id).find('input').each(function () {
            user[fields[i]] = this.value;
            i++;
        })

        return user;
    }

    $(document).on('click', '.receiveButton', function () {

        const data = setData("#receiveForm");

        console.log(data);
        fetch("http://localhost:8080/receive", {
            method: 'POST',
            headers: {
                'content-type': 'application/json; charset=utf-8'
            },
            body: JSON.stringify(data)
        }).then(function() {
            alert("Transaction successfully completed");
        });
    })
</script>
</body>
</html>
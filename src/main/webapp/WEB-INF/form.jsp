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
            <form id="sendForm" method="post">
                <h1>Fill form</h1>
                <div class="form-group">
                    <label for="sender-name">Sender name</label>
                    <input
                            class="form-control sender-name"
                            id="sender-name"
                            placeholder="Sender name..."
                            value="nurdas"
                    />
                </div>
                <div class="form-group">
                    <label for="sender-lastname">Sender lastname</label>
                    <input
                            class="form-control sender-lastname"
                            id="sender-lastname"
                            placeholder="Sender lastname..."
                            value="sadyrbekov"
                    />
                </div>
                <div class="form-group">
                    <label for="receiver-name">Receiver name</label>
                    <input
                            class="form-control sender-last-name"
                            id="receiver-name"
                            placeholder="Receiver name..."
                            value="marat"
                    />
                </div>
                <div class="form-group">
                    <label for="receiver-lastname">Receiver lastname</label>
                    <input
                            class="form-control sender-last-name"
                            id="receiver-lastname"
                            placeholder="Receiver lastname..."
                            value="basharov"
                    />
                </div>
                <div class="form-group">
                    <label for="amount">Amount</label>
                    <input
                            class="form-control sender-last-name"
                            id="amount"
                            placeholder="Amount..."
                            value="100"
                    />
                </div>
                <div class="form-group">
                    <label for="comment">Comment</label>
                    <input
                            class="form-control sender-last-name"
                            id="comment"
                            placeholder="Comment..."
                            value="thank you"
                    />
                </div>

                <button type="submit" class="sendButton btn btn-primary btn-customized mt-4" form="sendForm">
                    Send
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
        const fields = ["senderName", "senderLastname", "receiverName", "receiverLastname", "amount", "comment"];
        let data = {},
            sender ={},
            receiver ={};

        let i = 0;

        console.log(fields);

        $(id).find('input, select').each(function () {
            let field = fields[i];
            console.log(field);
            if(field.startsWith("sender")) {
                field = field.replace("sender", "").toLowerCase();
                sender[field] = this.value;
            } else if(field.startsWith("receiver")) {
                field = field.replace("receiver", "").toLowerCase();
                receiver[field] = this.value;
            } else {
                data[field] = this.value;
            }

            i++;
        })

        data["sender"] = sender;
        data["receiver"] = receiver;

        data["currency"] = "KGS";

        return data;
    }

    $(document).on('click', '.sendButton', function () {
        console.log("clicked button");
        const data = setData("#sendForm");

        console.log(data);

        let xhr = new XMLHttpRequest();
        xhr.open("POST", "http://localhost:8080/send", true);
        xhr.setRequestHeader('Content-Type', 'application/json');
        xhr.send(JSON.stringify(data));

        /*fetch("http://localhost:8080/send", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }).then(resp => {
            alert("Your secret code is " + resp.text() + ". Send it to receiver, so he can take his money");
        }).catch(error => {
            console.log(error);
        });*/
    })

</script>
</body>
</html>
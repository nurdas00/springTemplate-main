<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
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
    <div>
        <a href="/logout">Logout</a>
    </div>
    <div class="row align-items-center" style="height: 100vh;">
        <div class="mx-auto col-10">
            <form id="sendForm" method="post">
                <h1>Fill form</h1>
                <div class="form-group">
                    <label for="sender-name">Sender name</label>
                    <input
                            class="form-control"
                            id="sender-name"
                            placeholder="Sender name..."
                            th:value="${name}"
                            disabled
                    />
                </div>
                <div class="form-group">
                    <label for="sender-lastname">Sender lastname</label>
                    <input
                            class="form-control"
                            id="sender-lastname"
                            placeholder="Sender lastname..."
                            th:value="${lastname}"
                            disabled
                    />
                </div>
                <div class="form-group">
                    <label for="receiver-name">Receiver name</label>
                    <input
                            class="form-control"
                            id="receiver-name"
                            placeholder="Receiver name..."
                            value="marat"
                    />
                </div>
                <div class="form-group">
                    <label for="receiver-lastname">Receiver lastname</label>
                    <input
                            class="form-control"
                            id="receiver-lastname"
                            placeholder="Receiver lastname..."
                            value="basharov"
                    />
                </div>
                <div class="form-group">
                    <label for="amount">Amount</label>
                    <input
                            class="form-control"
                            placeholder="Amount..."
                            id="amount"
                            value="100"
                    />
                </div>
                <div class="form-group">
                    <label for="currency">Currency</label>
                    <select class="form-select" id="currency">
                        <option selected>KGS</option>
                        <option>KZT</option>
                        <option>RUB</option>
                        <option>USD</option>
                        <option>EUR</option>
                        <option>RMB</option>
                    </select>
                </div>
                <div class="form-group">
                    <label for="comment">Comment</label>
                    <input
                            class="form-control"
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
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>

<script>
    import axios from 'axios'

    function setData(id) {
        const fields = ["senderName", "senderLastname", "receiverName", "receiverLastname", "amount", "currency", "comment"];
        let data = {},
            sender = {},
            receiver = {};

        let i = 0;

        console.log(fields);

        $(id).find('input, select').each(function () {
            let field = fields[i];
            console.log(field);
            if (field.startsWith("sender")) {
                field = field.replace("sender", "").toLowerCase();
                sender[field] = this.value;
            } else if (field.startsWith("receiver")) {
                field = field.replace("receiver", "").toLowerCase();
                receiver[field] = this.value;
            } else {
                data[field] = this.value;
            }

            i++;
        })

        data["sender"] = sender;
        data["receiver"] = receiver;

        return data;
    }

    $(document).on('click', '.sendButton', async function () {
        console.log("clicked button");
        const data = setData("#sendForm");

        console.log(data);
        /*let response = await fetch("http://localhost:8080/send", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        }).catch(error => {
            console.log(error);
        });

        alert("Your secret code is " + await response.text() + ". Send it to receiver, so he can take his money");*/
    })

</script>
</body>
</html>
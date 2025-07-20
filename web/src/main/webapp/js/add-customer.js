document.addEventListener('DOMContentLoaded', function () {
    // NIC Front Image Preview
    // const nicFrontInput = document.getElementById('nicFront');
    // const nicFrontPreview = document.getElementById('nicFrontPreview');

    // nicFrontInput.addEventListener('change', function (e) {
    //     const file = e.target.files[0];
    //     if (file) {
    //         const reader = new FileReader();
    //
    //         reader.onload = function (event) {
    //             nicFrontPreview.innerHTML = '';
    //             const img = document.createElement('img');
    //             img.src = event.target.result;
    //             img.style.maxWidth = '100%';
    //             img.style.maxHeight = '150px';
    //             nicFrontPreview.appendChild(img);
    //
    //             // Add remove button
    //             const removeBtn = document.createElement('button');
    //             removeBtn.innerHTML = '&times;';
    //             removeBtn.className = 'remove-image-btn';
    //             removeBtn.addEventListener('click', function (e) {
    //                 e.preventDefault();
    //                 e.stopPropagation();
    //                 nicFrontPreview.innerHTML = '';
    //                 nicFrontInput.value = '';
    //             });
    //             nicFrontPreview.appendChild(removeBtn);
    //         };
    //
    //         reader.readAsDataURL(file);
    //     }
    // });

    // NIC Back Image Preview
    // const nicBackInput = document.getElementById('nicBack');
    // const nicBackPreview = document.getElementById('nicBackPreview');

    // nicBackInput.addEventListener('change', function (e) {
    //     const file = e.target.files[0];
    //     if (file) {
    //         const reader = new FileReader();
    //
    //         reader.onload = function (event) {
    //             nicBackPreview.innerHTML = '';
    //             const img = document.createElement('img');
    //             img.src = event.target.result;
    //             img.style.maxWidth = '100%';
    //             img.style.maxHeight = '150px';
    //             nicBackPreview.appendChild(img);
    //
    //             // Add remove button
    //             const removeBtn = document.createElement('button');
    //             removeBtn.innerHTML = '&times;';
    //             removeBtn.className = 'remove-image-btn';
    //             removeBtn.addEventListener('click', function (e) {
    //                 e.preventDefault();
    //                 e.stopPropagation();
    //                 nicBackPreview.innerHTML = '';
    //                 nicBackInput.value = '';
    //             });
    //             nicBackPreview.appendChild(removeBtn);
    //         };
    //
    //         reader.readAsDataURL(file);
    //     }
    // });
});


async function registerCustomer() {


    const data = {
        fullName: document.getElementById("fullName").value.trim(),
        firstName: document.getElementById("firstName").value.trim(),
        lastName: document.getElementById("lastName").value.trim(),
        dob: document.getElementById("dob").value,
        gender: document.getElementById("gender").value,
        nic: document.getElementById("nic").value.trim(),
        // nicFront: document.getElementById("nicFront").files[0],
        // nicBack: document.getElementById("nicBack").files[0],
        addressLine1: document.getElementById("addressLine1").value.trim(),
        addressLine2: document.getElementById("addressLine2").value.trim(),
        city: document.getElementById("city").value.trim(),
        postalCode: document.getElementById("postalCode").value.trim(),
        mobile: document.getElementById("mobile").value.trim(),
        email: document.getElementById("email").value.trim()
    };


    // if (!data.nicFront || !data.nicBack) {
    //     alert("Please upload both NIC images.");
    //     return;
    // }

    const formData = new FormData();
    for (const key in data) {
        formData.append(key, data[key]);
    }

    const response = await fetch("http://localhost:8080/unity_national_bank/admin/addCustomer", {
        method: "POST",
        body: formData
    });

    if (response.ok) {
        const resData = await response.json();
        console.log(resData); // Confirm JSON output

        if (resData.success === true) {
            alert("User Added Successfully");
            document.getElementById("customerRegistrationForm").reset();
            document.getElementById('nicFrontPreview').innerHTML = '';
            document.getElementById('nicBackPreview').innerHTML = '';
        } else {
            alert(resData.message || "Registration failed.");
        }
    } else {
        alert("Something went wrong in request, please try again.");
    }


}
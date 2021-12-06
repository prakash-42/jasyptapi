function submit() {

	base_url = "/jasyptapi";

	let mode = document.querySelector('input[name="mode"]:checked').value;

	let isStringEncryption = false;
	let inputElem = document.getElementById("input");
	let input, inputFile;
	if (inputElem.type == "text") {
		isStringEncryption = true;
		input = inputElem.value;
		if(input.length < 1){
			let result = document.getElementById("result");
			result.innerHTML = "Input String cannot be blank!";
			return
		}
	} else {
		inputFile = $("#input")[0].files[0];
		if (! inputFile) {
			let result = document.getElementById("result");
			result.innerHTML = "Input file is required!!";
			return
		}
	}

	// set url based on "mode" and "isStringEncryption"
	let url = "";
	if(isStringEncryption) {
		if (mode == "encrypt") {
			url = base_url + "/encrypt";
		} else {
			url = base_url + "/decrypt";
		}
	} else {
		if (mode == "encrypt") {
			url = base_url + "/file/encrypt";
		} else {
			url = base_url + "/file/decrypt";
		}
	}

	let password = document.getElementById("password").value;
	if(password.length < 1){
		let result = document.getElementById("result");
		result.innerHTML = "Password cannot be blank!";
		return
	}

	let algorithm = document.getElementById("algorithm").value;
	if(algorithm.length < 1){
		let result = document.getElementById("result");
		result.innerHTML = "algorithm cannot be blank!";
		return
	}

	let keyObtentionIterations = document.getElementById("keyObtentionIterations").value;
	if(keyObtentionIterations.length < 1){
		let result = document.getElementById("result");
		result.innerHTML = "keyObtentionIterations cannot be blank!";
		return
	}

	let providerName = document.getElementById("providerName").value;
	if(providerName.length < 1){
		let result = document.getElementById("result");
		result.innerHTML = "providerName cannot be blank!";
		return
	}

	let saltGeneratorClassName = document.getElementById("saltGeneratorClassName").value;
	if(saltGeneratorClassName.length < 1){
		let result = document.getElementById("result");
		result.innerHTML = "saltGeneratorClassName cannot be blank!";
		return
	}

	let ivGeneratorClassName = document.getElementById("ivGeneratorClassName").value;
	if(ivGeneratorClassName.length < 1){
		let result = document.getElementById("result");
		result.innerHTML = "ivGeneratorClassName cannot be blank!";
		return
	}

	let stringOutputType = document.getElementById("stringOutputType").value;
	if(stringOutputType.length < 1){
		let result = document.getElementById("result");
		result.innerHTML = "stringOutputType cannot be blank!";
		return
	}

	let result = document.getElementById("result");
	result.innerHTML = "Loading...";
	if(isStringEncryption) {
		jQuery.ajax({
		    type: 'GET',
		    url: url,
		    data: {input, password, algorithm, keyObtentionIterations, providerName, saltGeneratorClassName, ivGeneratorClassName, stringOutputType},
		    success: function(data, textStatus, jqXHR)
		    {
			    //data - response from server
			    let result = document.getElementById("result");
			    result.innerHTML = "result: " + data;
		    },
		    error: function (jqXHR, textStatus, errorThrown)
		    {
		        result.innerHTML = "Error occurred!!";
		        console.log("Error occurred!!");
		        console.log(jqXHR);
		        console.log(textStatus);
		        console.log(errorThrown);
		    }
		});
	} else {
		let data = new FormData();
		data.append("inputFile", inputFile);

		// https://stackoverflow.com/a/43108327
		let params = $.param({password, algorithm, keyObtentionIterations, providerName, saltGeneratorClassName, ivGeneratorClassName, stringOutputType});
		jQuery.ajax({
		    type: 'POST',
		    url: url + "?" + params,
		    data: data,
		    processData: false,  // tell jQuery not to process the data
		    contentType: false,   // tell jQuery not to set contentType
		    success: function(data, textStatus, jqXHR)
		    {
		    	var newWin = window.open();
			    newWin.document.write(data);
			    console.log(data);
			    result.innerHTML = "SUCCESS!! results sent to a new tab."
		    },
		    error: function (jqXHR, textStatus, errorThrown)
		    {
		        result.innerHTML = "Error occurred!!";
		        console.log("Error occurred!!");
		        console.log(jqXHR);
		        console.log(textStatus);
		        console.log(errorThrown);
		    }
		});
	}
}

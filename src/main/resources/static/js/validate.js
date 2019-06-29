jQuery.validator.addMethod("greaterThan",
    function (value, element, params) {

        if (!/Invalid|NaN/.test(new Date(value))) {
            return new Date(value) > new Date($(params).val());
        }

        return isNaN(value) && isNaN($(params).val())
            || (Number(value) > Number($(params).val()));
    }, 'Must be greater than {0}.');

validator = $('#projectForm').validate({
    rules: {
        projectnumber:{required:true, remote: { url: '/createproject/is-available-projectnumber'}},
        name:{required:true},
        customer:{required:true},
        group:{required:true},
        status:{required:true},
        startDate:{required:true},
        endDate:{greaterThan: "#datepickerStart"}
    },
    messages: {
        projectnumber:{required: '', remote: 'The project number already existed. Please select a different project number'},
        name:{required:''},
        customer:{required:''},
        group:{required:''},
        status:{required:''},
        startDate:{required:''},
        endDate: {greaterThan: ''}
    },
    errorClass: 'help-block text-danger errmessage_custom',
    highlight: function(e){
        $(e).addClass('is-invalid');
    },
    unhighlight: function(e) {
        $(e).removeClass('is-invalid');
    },
    invalidHandler: function (event, validator) {
        $("#alert-error").show();
    }

})
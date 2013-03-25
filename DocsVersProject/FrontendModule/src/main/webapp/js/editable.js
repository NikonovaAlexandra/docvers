$.fn.extend({
    editable: function() {
        alert("hh");
        var that = this,
            $edittextbox = $('<input type="text"></input>').css('min-width', that.width()),
            submitChanges = function() {
                that.html($edittextbox.val());
                that.show();
                that.trigger('editsubmit', [that.html()]);
                $(document).unbind('click', submitChanges);
                $edittextbox.detach();
            },
            tempVal;
        $edittextbox.click(function(event) {
            event.stopPropagation();
        });

        that.dblclick(function(e) {
            tempVal = that.html();
            $edittextbox.val(tempVal).insertBefore(that).bind('keypress', function(e) {
                if ($(this).val() !== '') {
                    var code = (e.keyCode ? e.keyCode : e.which);
                    if (code == 13) {
                        submitChanges();
                    }
                }
            });
            that.hide();
            $(document).click(submitChanges);
        });
        return that;
    }
});


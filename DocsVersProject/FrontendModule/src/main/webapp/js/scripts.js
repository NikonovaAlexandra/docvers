$(document).ready(function () {
    $("ul#nav li a").addClass("js");
    $("ul#nav li a").hover(
        function () {
            $(this).stop(true, true).animate({backgroundPosition: "(200px 0)"}, 100);
            $(this).animate({backgroundPosition: "(5px 30px)"}, 150);
        },
        function () {
            $(this).animate({backgroundPosition: "(160px 20px)"}, 200);

        }
    );

});
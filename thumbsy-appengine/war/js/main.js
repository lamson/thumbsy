//"use strict";

var currentConvId = 1;
var userId = '';

var helper = (function () {
    var BASE_API_PATH = 'plus/v1/';

    return {
        /**
         * Hides the sign in button and starts the post-authorization operations.
         *
         * @param {Object} authResult An Object which contains the access token and
         *   other authentication information.
         */
        onSignInCallback: function (authResult) {
            gapi.client.load('plus', 'v1', function () {
                $('#authResult').html('Auth Result:<br/>');
                for (var field in authResult) {
                    $('#authResult').append(' ' + field + ': ' +
                        authResult[field] + '<br/>');
                }

                // when signed-in
                if (authResult['access_token']) {

                    // request to get user google info
                    var request = gapi.client.plus.people.get({
                        'userId': 'me'
                    });

                    request.execute(function (resp) {
                        userId = resp.id;
                        console.log('ID: ' + resp.id);
                        console.log('Display Name: ' + resp.displayName);
                        console.log('Image URL: ' + resp.image.url);
                        console.log('Profile URL: ' + resp.url);
                    });

                    // show main app when user signed in
                    $('#main').show();

                    $('#authOps').show('slow');

                    // hide signin button
                    $('#gConnect').hide();

                    // display user profile top right
                    helper.profile();

                    // helper.people();


                } else if (authResult['error']) {
                    // There was an error, which means the user is not signed in.
                    // As an example, you can handle by writing to the console:
                    console.log('There was an error: ' + authResult['error']);
                    $('#authResult').append('Logged out');
                    $('#authOps').hide('slow');
                    $('#gConnect').show();
                }
                console.log('authResult', authResult);
            });
        },

        /**
         * Calls the OAuth2 endpoint to disconnect the app for the user.
         */
        disconnect: function () {
            // Revoke the access token.
            $.ajax({
                type: 'GET',
                url: 'https://accounts.google.com/o/oauth2/revoke?token=' +
                    gapi.auth.getToken().access_token,
                async: false,
                contentType: 'application/json',
                dataType: 'jsonp',
                success: function (result) {
                    console.log('revoke response: ' + result);
                    $('#authOps').hide();
                    $('#profile').empty();
                    $('#visiblePeople').empty();
                    $('#authResult').empty();
                    $('#gConnect').show();
                },
                error: function (e) {
                    console.log(e);
                }
            });
        },

        /**
         * Gets and renders the list of people visible to this app.
         */
        people: function () {
            var request = gapi.client.plus.people.list({
                'userId': 'me',
                'collection': 'visible'
            });
            request.execute(function (people) {
                $('#visiblePeople').empty();
                $('#visiblePeople').append('Number of people visible to this app: ' +
                    people.totalItems + '<br/>');
                for (var personIndex in people.items) {
                    person = people.items[personIndex];
                    $('#visiblePeople').append('<img src="' + person.image.url + '">');
                }
            });
        },

        /**
         * Gets and renders the currently signed in user's profile data.
         */
        profile: function () {
            var request = gapi.client.plus.people.get({'userId': 'me'});
            request.execute(function (profile) {
                $('#profile').empty();
                if (profile.error) {
                    $('#profile').append(profile.error);
                    return;
                }
                var span = $('<span>');
                var img = $('<img>');
                span.text(profile.displayName + ' ');
                img.attr('src', profile.image.url);
                $('#profile').append(span);
                $('#profile').append(img);


//                $('#profile').append(
//                    $('<span>' + profile.displayName + '   <img src=\"' + profile.image.url + '\"></span>'));
//                $('#profile').append(
//                    $('<p>Hello ' + profile.displayName + '!<br />Tagline: ' +
//                        profile.tagline + '<br />About: ' + profile.aboutMe + '</p>'));
//                if (profile.cover && profile.coverPhoto) {
//                    $('#profile').append(
//                        $('<p><img src=\"' + profile.cover.coverPhoto.url + '\"></p>'));
//                }

            });
        }


    };
})();

/**
 * Calls the helper method that handles the authentication flow.
 *
 * @param {Object} authResult An Object which contains the access token and
 *   other authentication information.
 */
function onSignInCallback(authResult) {
    helper.onSignInCallback(authResult);
}

/**
 * jQuery initialization
 */
$(document).ready(function () {
    $('#disconnect').click(helper.disconnect);
    $('#loaderror').hide();
    if ($('[data-clientid="YOUR_CLIENT_ID"]').length > 0) {
        alert('This sample requires your OAuth credentials (client ID) ' +
            'from the Google APIs console:\n' +
            '    https://code.google.com/apis/console/#:access\n\n' +
            'Find and replace YOUR_CLIENT_ID with your client ID.'
        );
    }
    $("#message-box").animate({ scrollTop: $('#message-box')[0].scrollHeight}, 1000);
});

/**
 * Add to underscore utility functions to allow optional usage
 * This will allow other storage options easier to manage, such as
 * 'localStorage'. This must be set on the model and collection to
 * be used on directly. Defaults to 'Backbone.sync' otherwise.
 * example Backbone.Model.extend({sync: _.bitBucketStorage});
 **/
_.mixin(
{

    /*
     * bitBucketSync overrides syncing models to the "bitbucket" 
     * (or better said 'saved no where')
     */
    bitBucketStorage : function(method, model, options) {
        console.log('bitbucketStorage called!');
    },
    /*
     * sessionStorage overrides syncing models to the local sessionstorage 
     * (Available within the better browsers!)
     */
    sessionStorage : function(method, model, options) {
        console.log('sessionStorage called!');
    }
});
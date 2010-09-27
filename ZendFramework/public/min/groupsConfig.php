<?php
/**
 * Groups configuration for default Minify implementation
 * @package Minify
 */

/** 
 * You may wish to use the Minify URI Builder app to suggest
 * changes. http://yourdomain/min/builder/
 **/

function jquery_fetch() {
    return file_get_contents('http://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js');
}

$jquery = new Minify_Source(array(
    'id' => 'jquery1',
    'getContentFunc' => 'jquery_fetch',
    'contentType' => Minify::TYPE_JS,    
    'lastModified' => ($_SERVER['REQUEST_TIME'] - $_SERVER['REQUEST_TIME'] % 86400),
));

function jqueryui_fetch() {
    return file_get_contents('http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.2/jquery-ui.min.js');
}

$jqueryui = new Minify_Source(array(
    'id' => 'jqueryui1',
    'getContentFunc' => 'jqueryui_fetch',
    'contentType' => Minify::TYPE_JS,    
    'lastModified' => ($_SERVER['REQUEST_TIME'] - $_SERVER['REQUEST_TIME'] % 86400),
));

function jquerytool_fetch() {
    return file_get_contents('http://cdn.jquerytools.org/1.2.5/jquery.tools.min.js');
}

$jquerytool = new Minify_Source(array(
    'id' => 'jquerytool1',
    'getContentFunc' => 'jquerytool_fetch',
    'contentType' => Minify::TYPE_JS,    
    'lastModified' => ($_SERVER['REQUEST_TIME'] - $_SERVER['REQUEST_TIME'] % 86400),
));

function swfobject_fetch() {
    return file_get_contents('http://ajax.googleapis.com/ajax/libs/swfobject/2.2/swfobject.js');
}

$swfobject = new Minify_Source(array(
    'id' => 'swfobject1',
    'getContentFunc' => 'swfobject_fetch',
    'contentType' => Minify::TYPE_JS,    
    'lastModified' => ($_SERVER['REQUEST_TIME'] - $_SERVER['REQUEST_TIME'] % 86400),
));

function yui_fetch() {
    return file_get_contents('http://yui.yahooapis.com/2.8.0r4/build/reset-fonts-grids/reset-fonts-grids.css');
}

$yuicss = new Minify_Source(array(
    'id' => 'yuicss',
    'getContentFunc' => 'yui_fetch',
    'contentType' => Minify::TYPE_CSS,    
    'lastModified' => ($_SERVER['REQUEST_TIME'] - $_SERVER['REQUEST_TIME'] % 86400),
));

return array(
    'js' => array($jquery
                 , $swfobject
                 , $jquerytool),
    'css' => array($yuicss
                  , '//css/base.css'
                  , '//css/styles.css'
                  , '//css/form.css')


);
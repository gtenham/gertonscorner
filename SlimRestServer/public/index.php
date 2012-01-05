<?php
ini_set('session.use_cookies', 0);
ini_set('session.use_only_cookies', 0);

set_include_path(implode(PATH_SEPARATOR, array(
    realpath(dirname(__FILE__) . '/../lib'),
    realpath(dirname(__FILE__) . '/../app'),
    get_include_path(),
)));

require 'Slim/Slim.php';

$app = new Slim(array(
    'session.handler' => null
));
$app->setName('root');

$app->get('/', function () use ($app) {
	echo "Running Slim app: ".$app->getName();
});


$app->run();
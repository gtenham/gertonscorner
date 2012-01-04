<?php
set_include_path(implode(PATH_SEPARATOR, array(
    realpath(dirname(__FILE__) . '/../lib'),
    realpath(dirname(__FILE__) . '/../app'),
    get_include_path(),
)));

require 'Slim/Slim.php';

$app = new Slim();
$app->setName('root');

$app->get('/', function () use ($app) {
	echo "Running Slim app: ".$app->getName();
});


$app->run();
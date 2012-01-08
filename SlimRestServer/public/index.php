<?php
// Use no session cookies (being stateless!)
ini_set('session.use_cookies', 0);
ini_set('session.use_only_cookies', 0);
// Use Oracle pooled connections
ini_set('oci8.connection_class', 'slimserver');
//Set include path
set_include_path(implode(PATH_SEPARATOR, array(
    realpath(dirname(__FILE__) . '/../lib'),
    realpath(dirname(__FILE__) . '/../app'),
    get_include_path(),
)));
// Autoload classes found in include path
spl_autoload_register();
// Define path to application directory
defined('APPLICATION_PATH')
    || define('APPLICATION_PATH',
              realpath(dirname(__FILE__) . '/../app'));
              
require 'Slim/Slim.php';

$app = new Slim(array(
    'session.handler' => null
));
$app->setName('root');

$app->get('/', function () use ($app) {
	echo "Running Slim app: ".$app->getName();
});

$app->get('/todos/:id', function ($id) {
	$todoservice = new services\TodoService();
	$todo = $todoservice->getTodoById($id);
	echo json_encode($todo->toArray());
});

$app->run();
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

$todoservice = new services\TodoService();

$app->get('/todos', function () use ($app, $todoservice) {
	$app->etag(md5(serialize($todoservice->getTodos())));
	$todolist['todos'] = $todoservice->getTodos();
	echo json_encode($todolist);
}); 

$app->get('/todos/:id', function ($id) use ($app, $todoservice) {
	$app->etag(md5(serialize($todoservice->getTodoById($id))));
	echo json_encode($todoservice->getTodoById($id));
});

$app->run();
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
    'session.handler' => null,
	'templates.path' => '../app/views',
	'debug' => false
));
$app->setName('todos');

$app->get('/', function () use ($app) {
	$app->render('TodoHome.php', array( 'name' => $app->getName() ));
});

$app->error(function ( Exception $e ) use ($app) {
	if ($e->__toString() == null) {
		$app->halt(500, $e->getMessage());
	} else {
		$app->halt($e->__toString());
	}
});

// Start of Todo application REST routing
$todoservice = new services\TodoService();

$app->get('/todos', function () use ($app, $todoservice) {
	$app->etag(md5(serialize($todoservice->getTodos())));
	echo json_encode($todoservice->getTodos());
}); 

$app->get('/todos/:id', function ($id) use ($app, $todoservice) {
	$app->etag(md5(serialize($todoservice->getTodoById($id))));
	echo json_encode($todoservice->getTodoById($id));
});

$app->post('/todos', function () use ($app, $todoservice) {
	$data = json_decode($app->request()->getBody(),true);
	$todoservice->addTodo($data);
});

$app->delete('/todos/:id', function ($id) use ($todoservice) {
	$todoservice->removeTodo($id);
});

// Run slim application
$app->run();
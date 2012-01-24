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

// Create new Dependency Injection container
$di = new Sf\Pimple();

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
		$app->halt($e->getCode(), $e->getMessage());
	}
});

// Start of Todo application REST routing
$di['todoservice'] = $di->share(function() {
  return new services\TodoService();
});
$di['etag'] = $di->share(function() {
  return new services\EtagService();
});

$app->get('/todos', function () use ($app, $di) {
	$etag = $di['etag']->get('/todos');
	$app->etag($etag);
	if ($app->request()->headers('If-None-Match') != $etag) {
		
		$todoservice = $di['todoservice'];
		//$app->etag(md5(serialize($todoservice->getTodos())));
		echo json_encode($todoservice->getTodos());
	} else {echo 'not changed';}
}); 

$app->get('/todos/:id', function ($id) use ($app, $di) {
	$app->etag($di['etag']->get('/todos/'.$id));
	$todoservice = $di['todoservice'];
	//$app->etag(md5(serialize($todoservice->getTodoById($id))));
	echo json_encode($todoservice->getTodoById($id));
});

$app->post('/todos', function () use ($app, $di) {
	$todoservice = $di['todoservice'];
	$data = json_decode($app->request()->getBody(),true);
	$todo = $todoservice->addTodo($data);
	$di['etag']->change('/todos');
	$app->response()->header('Location', $app->request()->getResourceUri().'/'.$todo->id);
	$app->response()->status(201);
});

$app->put('/todos/:id', function ($id) use ($app, $di) {
	$todoservice = $di['todoservice'];
	$data = json_decode($app->request()->getBody(),true);
	$di['etag']->change('/todos/'.$id);
	echo json_encode($todoservice->updateTodo($data));
});

$app->delete('/todos/:id', function ($id) use ($di) {
	$todoservice = $di['todoservice'];
	$todoservice->removeTodo($id);
	$di['etag']->remove('/todos/'.$id);
});

// Run slim application
$app->run();
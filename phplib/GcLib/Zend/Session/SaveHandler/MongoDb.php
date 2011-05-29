<?php
/**
 * @see Zend_Session
 */
require_once 'Zend/Session.php';

class GcLib_Zend_Session_SaveHandler_MongoDb implements Zend_Session_SaveHandler_Interface {
	
	const MONGO_SERVER = 'servers';
	
	/**
     * MongoDb connection
     *
     * @var Mongo
     */
    protected $_conn;
    
    /**
     * Mongo servers
     *
     * @var string
     */
    protected $_servernames;
    
    /**
     * Mongo connection options
     *
     * @var array
     */
    protected $_connectoptions;
    
    /**
     * Mongo database
     *
     * @var MongoDB
     */
    protected $_db;
    
    /**
     * Mongo collection
     *
     * @var MongoCollection
     */
    protected $_collection;
    
    /**
     * Constructor
     *
     * @param array|Zend_Config $config
     * @throws Zend_Session_SaveHandler_Exception
     * @return void
     */
    public function __construct($config) {
    	if ($config instanceof Zend_Config) {
            $config = $config->toArray();
        } else if (!is_array($config)) {
            /**
             * @see Zend_Session_SaveHandler_Exception
             */
            require_once 'Zend/Session/SaveHandler/Exception.php';

            throw new Zend_Session_SaveHandler_Exception(
                '$config must be an instance of Zend_Config or array of key/value pairs containing '
              . 'configuration options for GcLib_Zend_Session_SaveHandler_MongoDb.');
        }
        foreach ($config as $key => $value) {
            do {
                switch ($key) {
                	case self::MONGO_SERVER:
                        $this->_servernames = $value;
                        break;
                    default:
                        // unrecognized options passed to mongodb connection options
                        break 2;
                }
                unset($config[$key]);
            } while (false);
        }
        
        $this->_connectoptions = $config;
	}
	
	/**
     * Destructor
     *
     * @return void
     */
    public function __destruct() {
        Zend_Session::writeClose();
    }
    
    /**
     * Open a mongodb connection
     *
     * @return Mongo $_conn
     */
	public function getConnection() {
	   if (!$this->_conn) {
	   	   $this->_conn = new Mongo($this->_servernames,$this->_connectoptions);
	   	}
		return $this->_conn;
	}
	
	/**
     * Open Session - retrieve resources
     *
     * @param string $save_path
     * @param string $name
     */
    public function open($save_path, $name) {
    	$this->_conn = $this->getConnection();
    	$this->_db = $this->_conn->php;
    	$this->_collection = $this->_db->sessions;
    	return true;
    }

    /**
     * Close Session - free resources
     *
     * @return boolean
     */
    public function close() {
    	//$this->_conn->close();
    	return true;
    }

    /**
     * Read session data
     *
     * @param string $id
     * @return string
     */
    public function read($id) {
    	$return = '';
    	
    	$session = $this->_collection->findOne(array('sessionid'=>$id));
    	if(!empty($session)) {
    		$return = $session['data'];
  		}
    	return $return;
    }

    /**
     * Write Session - commit data to resource
     *
     * @param string $id
     * @param mixed $data
     * @return boolean
     */
    public function write($id, $data) {
    	$return = false;
    	$collection = $this->_db->sessions;
    	
    	$newdata = array('sessionid' => $id,
    					 'lifetime' => new MongoDate(time()),
                      	 'data' => (string) $data);
    	$return = $this->_collection->update(array('sessionid' => $id), $newdata, array("upsert" => true));		 
    				 
    	return $return;
    }

    /**
     * Destroy Session - remove data from resource for
     * given session id
     *
     * @param string $id
     * @return boolean
     */
    public function destroy($id) {
    	$return = false;
    	$return = $this->_collection->remove(array('sessionid' => $id), array("justOne" => true));
    	return $return;
    }

    /**
     * Garbage Collection - remove old session data older
     * than $maxlifetime (in seconds)
     *
     * @param int $maxlifetime
     * @return boolean
     */
    public function gc($maxlifetime) {
    	$return = $this->_collection->remove(array('lifetime' => array('$lte' => new MongoDate(time()+$maxlifetime))));
    	return true;
    }
}
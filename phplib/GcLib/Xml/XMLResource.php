<?php
/**
 * The XML Resource class containing all XML based resources in an array.
 *
 * This class cannot be instantiated! Please get the xml resource as follows:
 *
 * <code>
 * $this->xsltdata = &XMLResource::load('/path/to/xslt/file.xsl');
 * </code>
 */
Class GcLib_Xml_XMLResource {
	
    private static $xmlResources = Array();
	
	/**
	 * Private constructor
	 * 
	 * @return void
	 */
	private function __construct() {}
	
	/**
	 * Private destructor
	 * 
	 * @return void
	 */
	private function __destruct() {}
	
	/**
     * Get the resource data given by the resourcePath.
     *
     * @param  string $resourcePath
     * @return String
     */
	public static function &load($resourcePath) {
		if (!array_key_exists($resourcePath, self::$xmlResources)) {
			
        	self::$xmlResources[$resourcePath] = file_get_contents($resourcePath);
        	
        }
        return self::$xmlResources[$resourcePath];
    }
    
    /**
     * Remove the resource data for the given resourcePath.
     *
     * @param  string $resourcePath
     */
    public static function remove($resourcePath) {
    	unset(self::$xmlResources[$resourcePath]);
    }
}
?>
<?php
namespace GcLib\Xml;

class XMLType {
	
	private $xmlDoc;
	
	/**
	 * Constructor
	 * 
	 * @param $xml XML data as a String
	 */
	function __construct($xml) {
	   $this->xmlDoc = new DOMDocument('1.0','UTF-8');
	   $this->xmlDoc->loadXML($xml);
	}
	
	/**
	 * XSLT 1.0 transformer method
	 * 
	 * @param $xslt XSLT stylesheet as a string
	 * @param $params XSLT stylesheet parameters as an Array
	 * @return XML as a String
	 */
	function transform($xslt, $params=null) {
	   
	   $xslDoc = new DOMDocument('1.0','UTF-8');
	   $xslDoc->loadXML($xslt);
	   
	   $proc = new XSLTProcessor();
       $proc->importStylesheet($xslDoc);
       
       // Set stylesheet parameter values
       if ($params!=null) {
          foreach ($params as $paramname => $paramvalue) {
       	     $proc->removeParameter('', $paramname);
             $proc->setParameter('', $paramname, $paramvalue);
          }
       }
       
       return $proc->transformToXML($this->xmlDoc);
	}
	
	/**
	 * Resolve XIncludes
	 * 
	 * @return XML as a String
	 */
	function resolveXinclude() {
		$this->xmlDoc->xinclude();
		return $this->xmlDoc->saveXML();
	}
	
	/**
	 * Pretty print XML output
	 * 
	 * @return XML as a String
	 */
	function prettyPrint() {
		$this->xmlDoc->preserveWhiteSpace = false;
		$this->xmlDoc->formatOutput = true;
		return $this->xmlDoc->saveXML();
	}
}
?>
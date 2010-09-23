<?php
/**
 * App_View_Helper_ShowInfo - Application specific View Helper 
 *
 * Copyright (c) 2010 Gerton ten Ham
 * Examples and documentation at: http://gertonscorner.wordpress.com
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *
 * @category   App
 * @package    App_View_Helper
 * @copyright  Copyright (c) 2010 Gerton ten Ham
 * @version    $Id: Abstract.php 65 2010-09-09 14:38:42Z gerton.tenham@yahoo.com $
 */
class App_View_Helper_ShowInfo extends Zend_View_Helper_Abstract {
	
	/**
	 * Show info tooltip for given property in path.
	 * If an error for the particular property occurs, then the first
	 * error message is shown.
	 * 
	 * @param string $path
	 * @param string $info (optional)
	 */
	public function ShowInfo($path, $info=null) {
		$pathArray = explode(".",$path);
		$bind = $this->view;
		$length = count($pathArray);
		$obj = $bind;
		foreach ($pathArray as $key => $val) {
			$bind = $bind->$val;
			if ($key === $length-2) {
				$obj = $bind;
			} else if ($key === $length-1) {
				$propertyName = $val;
			}
		}
		$errors = $obj->getErrors($propertyName);
		if (count($errors) > 0) {
			$infoBlock = '<span class="tooltip error" title="'.$errors[0]->getErrorMessage().'">&nbsp;</span>';
		} else if ($info !== null) {
			$infoBlock = '<span class="tooltip info" title="'.$info.'">&nbsp;</span>';
		} else {
			$infoBlock = "";
		}
		
		return $infoBlock;
	}
}
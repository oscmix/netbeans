<?php
// Licensed to the Apache Software Foundation (ASF) under one or more contributor license agreements; and to You under the Apache License, Version 2.0.
abstract class AbstarctClass {

    abstract function abstractImplicitPublic();
    abstract public function abstractPublic();
    abstract protected function abstractProtected();
    // can't be declared private
    // abstract private function abstractPrivate();
    abstract static function abstractImplicitPublicStatic();
    abstract public static function abstractPublicStatic();
    abstract protected static function abstractProtectedStatic();
}
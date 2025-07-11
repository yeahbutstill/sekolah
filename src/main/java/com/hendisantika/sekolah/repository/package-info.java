@org.springframework.lang.NonNullApi // blocks any repo interface in this package from ever returning null.
// Instead of returning null, and Repo method would throw an exception.
// => you'll have to use Optional<>
package com.hendisantika.sekolah.repository;

/*
 * Copyright 2013-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package example.springdata.jpa.simple;

import jakarta.persistence.*;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.util.ProxyUtils;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Sample user class.
 *
 * @author Oliver Gierke
 * @author Thomas Darimont
 */
@Entity
@NamedQuery(name = "User.findByTheUsersName", query = "from User u where u.username = ?1")
public class User {

	@Id
	@GeneratedValue(generator = "random-int-id")
	@GenericGenerator(name = "random-int-id", type = RandomIntGenerator.class)
	private Long id;

	private static final long serialVersionUID = -2952735933715107252L;

	@Column(unique = true) private String username;

	private String firstname;
	private String lastname;

	public User() {
		this(null);
	}

	/**
	 * Creates a new user instance.
	 */
	public User(Long id) {
		this.setId(id);
	}

	public User(String firstname, String lastname) {
		this.firstname = firstname;
		this.lastname = lastname;
	}

	/**
	 * Returns the username.
	 *
	 * @return
	 */
	public String getUsername() {

		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the firstname
	 */
	public String getFirstname() {
		return firstname;
	}

	/**
	 * @param firstname the firstname to set
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	/**
	 * @return the lastname
	 */
	public String getLastname() {
		return lastname;
	}

	/**
	 * @param lastname the lastname to set
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	private static class RandomIntGenerator implements IdentifierGenerator {

		@Override
		public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
			return ThreadLocalRandom.current().nextLong();
		}
	}

	@Transient // DATAJPA-622
	public boolean isNew() {
		return null == getId();
	}

	@Override
	public String toString() {
		return String.format("Entity of type %s with id: %s", this.getClass().getName(), getId());
	}

	@Override
	public boolean equals(Object obj) {

		if (null == obj) {
			return false;
		}

		if (this == obj) {
			return true;
		}

		if (!getClass().equals(ProxyUtils.getUserClass(obj))) {
			return false;
		}

		User that = (User) obj;

		return null == this.getId() ? false : this.getId().equals(that.getId());
	}

	@Override
	public int hashCode() {

		int hashCode = 17;

		hashCode += null == getId() ? 0 : getId().hashCode() * 31;

		return hashCode;
	}
}

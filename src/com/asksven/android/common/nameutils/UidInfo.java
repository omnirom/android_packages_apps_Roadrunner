/*
 * Copyright (C) 2011 asksven
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.asksven.android.common.nameutils;

/**
 * @author sven
 *
 */
public class UidInfo
{
	private int m_uid;
	private String m_uidName = "";
	private String m_uidNamePackage = "";
	private boolean m_uidUniqueName = false;
	
	public UidInfo()
	{
		
	}

	/**
	 * @return the m_uid
	 */
	public int getUid()
	{
		return m_uid;
	}

	/**
	 * @param m_uid the m_uid to set
	 */
	public void setUid(int m_uid)
	{
		this.m_uid = m_uid;
	}

	/**
	 * @return the uidName
	 */
	public String getName()
	{
		return m_uidName;
	}

	/**
	 * @param uidName the uidName to set
	 */
	public void setName(String uidName)
	{
		this.m_uidName = uidName;
	}

	/**
	 * @return the uidNamePackage
	 */
	public String getNamePackage()
	{
		return m_uidNamePackage;
	}

	/**
	 * @param uidNamePackage the uidNamePackage to set
	 */
	public void setNamePackage(String uidNamePackage)
	{
		this.m_uidNamePackage = uidNamePackage;
	}

	/**
	 * @return the uidUniqueName
	 */
	public boolean isUniqueName()
	{
		return m_uidUniqueName;
	}

	/**
	 * @param uidUniqueName the uidUniqueName to set
	 */
	public void setUniqueName(boolean uidUniqueName)
	{
		this.m_uidUniqueName = uidUniqueName;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UidInfo [m_uid=" + m_uid + ", m_uidName=" + m_uidName
				+ ", m_uidNamePackage=" + m_uidNamePackage
				+ ", m_uidUniqueName=" + m_uidUniqueName + "]";
	}
	
	
}

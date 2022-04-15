package doublewhaleapi.dwapi.DataModels;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import doublewhaleapi.dwapi.DataModels.GuildModel.RoleGuild;

/**
 * PlayerModel class to store guild player data.
 * 
 * @author BlackWarlow
 *
 */
public class PlayerModel {
	private String name;
	private Boolean active;
	private RoleGuild role;

	/**
	 * Simple constructor.
	 * 
	 * @param name   Player name
	 * @param active Boolean is player active, if null will be set to false
	 * @param role   RoleGuild role value of created player
	 */
	public PlayerModel(String name, @Nullable Boolean active, @Nullable RoleGuild role) {
		this.name = name;
		if (active == null)
			active = false;
		this.active = active;
		if (role == null)
			role = RoleGuild.Requested;
		this.role = role;
	}

	/**
	 * name getter.
	 * 
	 * @return name Player name
	 */
	public String getName() {
		return name;
	}

	/**
	 * name setter.
	 * 
	 * @param name Player name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * active getter.
	 * 
	 * @return active Player status
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * active setter.
	 * 
	 * @param active Player status
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * role getter.
	 * 
	 * @return role type of RoleGuild enum
	 */
	public RoleGuild getRole() {
		return role;
	}

	/**
	 * role setter.
	 * 
	 * @param role type of RoleGuild enum
	 */
	public void setRole(RoleGuild role) {
		this.role = role;
	}

	/**
	 * Tests if player has Member level permissions in a guild - so roles Creator,
	 * Operator and Member, but not Requested.
	 * 
	 * @return true if player's role is Creator, Operator or Member, false otherwise
	 */
	public Boolean testMembership() {
		return (role == RoleGuild.Member) || (role == RoleGuild.Operator) || (role == RoleGuild.Creator);
	}

	/**
	 * Tests if player has Operator level permissions in a guild - so roles Creator
	 * and Operator.
	 * 
	 * @return true if player's role is Operator or Creator, false otherwise
	 */
	public Boolean testOperatorship() {
		return (role == RoleGuild.Operator) || (role == RoleGuild.Creator);
	}

	/**
	 * Tests if player's role is Creator level.
	 * 
	 * @return true if player's role is Creator, false otherwise
	 */
	public Boolean testCreatorship() {
		return role == RoleGuild.Creator;
	}

	/**
	 * Tests for upper available permission by RoleGuild role.
	 * 
	 * @param testRole Lowest role to test
	 * @return true if player possesses this role, false otherwise
	 */
	@Nonnull
	public Boolean testRole(RoleGuild testRole) {
		switch(testRole) {
			case Creator: return testCreatorship();
			case Operator: return testOperatorship();
			case Member: return testMembership();
			case Requested: return !testMembership();
		}
		return false;
	}
}

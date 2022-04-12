package doublewhaleapi.dwapi.DataModels;

import javax.annotation.Nullable;

import doublewhaleapi.dwapi.DataModels.GuildModel.RoleGuild;

/**
 * PlayerModel class to store guild player data
 * 
 * @author BlackWarlow
 *
 */
public class PlayerModel {
	private String name;
	private Boolean active;
	private RoleGuild role;

	/**
	 * Simple constructor
	 * 
	 * @param name
	 * @param active Boolean is player active, if null will be set to false
	 * @param role   RoleGuild role value of created player
	 */
	public PlayerModel(String name, @Nullable Boolean active, RoleGuild role) {
		this.name = name;
		if (active == null)
			active = false;
		this.active = active;
		this.role = role;
	}

	/**
	 * name getter
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * name setter
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * active getter
	 * 
	 * @return active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * active setter
	 * 
	 * @param active
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * role getter
	 * 
	 * @return role type of RoleGuild enum
	 */
	public RoleGuild getRole() {
		return role;
	}

	/**
	 * role setter
	 * 
	 * @param role type of RoleGuild enum
	 */
	public void setRole(RoleGuild role) {
		this.role = role;
	}

	/**
	 * Tests if player has Member level permissions in a guild - so roles Creator,
	 * Operator and Member, but not Requested
	 * 
	 * @return true if player's role is Creator, Operator or Member, false otherwise
	 */
	public boolean testMembership() {
		return (role == RoleGuild.Member) || (role == RoleGuild.Opperator) || (role == RoleGuild.Creator);
	}

	/**
	 * Tests if player has Operator level permissions in a guild - so roles Creator
	 * and Operator
	 * 
	 * @return true if player's role is Operator or Creator, false otherwise
	 */
	public boolean testOperatorship() {
		return (role == RoleGuild.Opperator) || (role == RoleGuild.Creator);
	}

	/**
	 * Tests if player's role is Creator level
	 * 
	 * @return true if player's role is Creator, false otherwise
	 */
	public boolean testCreatorship() {
		return role == RoleGuild.Creator;
	}
}

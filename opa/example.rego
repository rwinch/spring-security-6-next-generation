package spring
import future.keywords.in

default allow := false

allow := true {
	count(grant) > 0
}

hasRole(role) {
	concat("", ["ROLE_", role]) in input.user.authorities
}

grant["/user hasRole USER"] {
	input.request.uri == "/user"
	hasRole("USER")
}

grant["/ allowed"] {
    input.request.uri == "/"
}
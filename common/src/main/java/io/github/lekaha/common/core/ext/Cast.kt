package io.github.lekaha.common.core.ext

object DtoCaster {
    inline fun <reified Entity, reified Dto> asDtos(collection: Collection<Entity>, cast: (Entity) -> Dto) =
        collection.map {
            asDto(it, cast)
        }

    inline fun <reified Entity, reified Dto> asDto(entity: Entity, cast: (Entity) -> Dto) = cast(entity)
}


// TODO: Reflection approach
//inline fun <reified A, reified B> A.cast(callable: KCallable<B>) = with(callable) {
//    val propertiesByName = A::class.members.associateBy { it.name }
//    callBy(parameters.associate { parameter ->
//        parameter to when (parameter.name) {
//            else -> propertiesByName[parameter.name]
//        }
//    })
//}